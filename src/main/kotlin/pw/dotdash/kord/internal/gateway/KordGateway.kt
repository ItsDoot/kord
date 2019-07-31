package pw.dotdash.kord.internal.gateway

import io.ktor.client.HttpClient
import io.ktor.client.features.websocket.ClientWebSocketSession
import io.ktor.client.features.websocket.wss
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.readText
import io.ktor.http.cio.websocket.send
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.serializer
import mu.KotlinLogging
import pw.dotdash.kord.internal.KordImpl
import pw.dotdash.kord.internal.KordImpl.Companion.JSON
import pw.dotdash.kord.internal.event.LazyEvent
import pw.dotdash.kord.internal.event.ReadyEventImpl
import pw.dotdash.kord.internal.event.message.CreateMessageEventImpl
import pw.dotdash.kord.internal.serial.nullable

class KordGateway(val kord: KordImpl, private val token: String, val http: HttpClient) {

    private val logger = KotlinLogging.logger("KordGateway")

    var heartbeatJob: Job? = null
    var heartbeatAck: Boolean = true
    var lastSeq: Int? = null

    private val heartbeatPayload: GatewayPayload<Int?>
        get() = GatewayPayload(
            op = 1,
            d = lastSeq
        )

    private val identifyPayload: GatewayPayload<GatewayPayload.Identify>
        get() = GatewayPayload(
            op = 2,
            d = GatewayPayload.Identify(
                token,
                GatewayPayload.Identify.Properties(System.getProperty("os.name"), "kord", "kord"),
                guildSubscriptions = false
            )
        )

    suspend fun connect() {
        val gatewayInfo = requireNotNull(kord.http.get("gateway/bot", GatewayInfo.serializer())) {
            "Failed to fetch gateway information"
        }

        http.wss(urlString = gatewayInfo.url + "/?v=6&encoding=json", block = this::handleSession)
    }

    private suspend fun handleSession(session: ClientWebSocketSession) {
        println("Connected to the gateway.")

        for (frame in session.incoming) {
            when (frame) {
                is Frame.Text -> {
                    val payload = JSON.parse(GatewayPayload.serializer(JsonElement.serializer().nullable), frame.readText())
                    this.handlePayload(session, payload)
                }
                is Frame.Close -> {
                    println("Websocket closed by host.")
                }
            }
        }
    }

    private suspend fun handlePayload(session: ClientWebSocketSession, payload: GatewayPayload<JsonElement?>) {
        when (payload.op) {
            0 -> { // Dispatch
                handleDispatch(session, payload)
            }
            1 -> { // Heartbeat
                session.sendHeartbeat()
            }
            7 -> { // Reconnect
                println("Received reconnect")
                session.close()
            }
            9 -> { // Invalid Session
                println("Received invalid session")
                session.close()
            }
            10 -> { // Hello
                val interval = payload.d!!.jsonObject.getPrimitive("heartbeat_interval").long

                this.heartbeatJob = session.launch {
                    loop@ while (true) {
                        if (heartbeatAck) {
                            heartbeatAck = false
                        } else {
                            session.close(IllegalStateException("Last heartbeat was not acknowledged"))
                            break@loop
                        }

                        session.sendHeartbeat()
                        delay(interval)
                    }
                }

                session.sendIdentify()
            }
            11 -> { // Heartbeat Ack
                heartbeatAck = true
                println("Heartbeat acknowledged.")
            }
            else -> {
                println("Unhandled payload op: ${payload.op}")
            }
        }
    }

    private suspend fun handleDispatch(session: ClientWebSocketSession, payload: GatewayPayload<JsonElement?>) {
        val seq = payload.s!!
        val type = payload.t!!
        val data = payload.d!!

        this.lastSeq = seq

        val event: LazyEvent = when (type) {
            "READY" -> JSON.fromJson(ReadyEventImpl.serializer(), data)
            "MESSAGE_CREATE" -> JSON.fromJson(CreateMessageEventImpl.serializer(), data)
            else -> {
                println("Unhandled dispatch event $type: $payload")
                return
            }
        }

        event.init(kord)

        kord.events.send(event)
    }

    private suspend fun <T> ClientWebSocketSession.send(payload: GatewayPayload<T>, d: KSerializer<T>) {
        this.send(JSON.stringify(GatewayPayload.serializer(d), payload))
    }

    private suspend fun ClientWebSocketSession.sendHeartbeat() {
        println("Sending heartbeat...")
        this.send(heartbeatPayload, Int.serializer().nullable)
    }

    private suspend fun ClientWebSocketSession.sendIdentify() {
        println("Identifying...")
        this.send(identifyPayload, GatewayPayload.Identify.serializer())
    }
}
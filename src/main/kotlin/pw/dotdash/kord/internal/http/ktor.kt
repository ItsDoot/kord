package pw.dotdash.kord.internal.http

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.websocket.WebSockets
import io.ktor.util.KtorExperimentalAPI

@UseExperimental(KtorExperimentalAPI::class)
internal fun newHttpClient(): HttpClient = HttpClient(CIO) {
    install(WebSockets)
}
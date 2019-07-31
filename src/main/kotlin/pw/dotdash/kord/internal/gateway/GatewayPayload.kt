package pw.dotdash.kord.internal.gateway

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GatewayPayload<out T>(
    val op: Int,
    val s: Int? = null,
    val t: String? = null,
    val d: T
) {

    @Serializable
    data class Hello(
        @SerialName("heartbeat_interval") val heartbeatInterval: Int
    )

    @Serializable
    data class Identify(
        val token: String,
        val properties: Properties,
        val compress: Boolean = false,
        @SerialName("guild_subscriptions") val guildSubscriptions: Boolean = true
    ) {

        @Serializable
        data class Properties(
            val `$os`: String,
            val `$browser`: String,
            val `$device`: String
        )
    }
}
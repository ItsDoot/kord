package pw.dotdash.kord.internal.entity.webhook

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import pw.dotdash.kord.internal.KordImpl
import pw.dotdash.kord.api.entity.webhook.Webhook
import pw.dotdash.kord.internal.entity.LazyEntity
import pw.dotdash.kord.internal.entity.user.UserImpl

@Serializable
data class WebhookImpl(
    override val id: Long,
    @SerialName("guild_id") override val guildId: Long?,
    @SerialName("channel_id") override val channelId: Long,
    override val user: UserImpl? = null,
    override val name: String?,
    override val avatar: String?,
    override val token: String
) : Webhook, LazyEntity {
    @Transient
    override lateinit var kord: KordImpl

    override suspend fun init(kord: KordImpl) {
        this.kord = kord
    }
}
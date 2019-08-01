package pw.dotdash.kord.internal.event.message

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import pw.dotdash.kord.api.entity.message.Message
import pw.dotdash.kord.api.event.message.DeleteMessageEvent
import pw.dotdash.kord.internal.KordImpl
import pw.dotdash.kord.internal.event.LazyEvent

@Serializable
data class DeleteMessageEventImpl(
    @SerialName("id") override val messageId: Long,
    @SerialName("channel_id") override val channelId: Long,
    @SerialName("guild_id") override val guildId: Long? = null
) : DeleteMessageEvent, LazyEvent {
    @Transient
    override lateinit var kord: KordImpl

    @Transient
    override val message: Message? = null

    override fun init(kord: KordImpl) {
        this.kord = kord
    }
}
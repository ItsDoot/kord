package pw.dotdash.kord.internal.entity.channel

import io.ktor.client.request.parameter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import pw.dotdash.kord.api.entity.channel.ChannelType
import pw.dotdash.kord.api.entity.channel.GuildTextChannel
import pw.dotdash.kord.api.entity.message.Message
import pw.dotdash.kord.internal.KordImpl
import pw.dotdash.kord.internal.entity.LazyEntity
import pw.dotdash.kord.internal.entity.message.MessageImpl
import pw.dotdash.kord.internal.http.TypedBody
import pw.dotdash.kord.internal.serial.OffsetDateTimeSerializer
import java.time.OffsetDateTime

@Serializable
data class GuildTextChannelImpl(
    override val id: Long,
    @Serializable(ChannelType.Companion::class) override val type: ChannelType,
    @SerialName("guild_id") override val guildId: Long,
    override val position: Int,
    @SerialName("permission_overwrites") override val overwrites: List<OverwriteImpl>,
    override val name: String,
    override val nsfw: Boolean,
    @SerialName("parent_id") override val parentId: Long?,
    override val topic: String?,
    @SerialName("last_message_id") override val lastMessageId: Long?,
    @SerialName("rate_limit_per_user") override val rateLimitPerUser: Int,
    @SerialName("last_pin_timestamp") @Serializable(OffsetDateTimeSerializer::class) override val lastPinTimestamp: OffsetDateTime? = null
) : GuildTextChannel, LazyEntity {
    @Transient
    override lateinit var kord: KordImpl

    override suspend fun init(kord: KordImpl) {
        this.kord = kord
    }

    override suspend fun getMessages(around: Long?, before: Long?, after: Long?, limit: Int?): List<MessageImpl> {
        return kord.http.getEntityList("channels/$id/messages", MessageImpl.serializer()) {
            if (around != null) parameter("around", around)
            if (before != null) parameter("before", before)
            if (after != null) parameter("after", after)
            if (limit != null) parameter("limit", limit)
        }
    }

    override suspend fun getMessage(id: Long): MessageImpl? =
        kord.http.getEntity("channels/${this.id}/messages/$id", MessageImpl.serializer())

    override suspend fun createMessage(build: Message.Builder.() -> Unit): MessageImpl? {
        val body = TypedBody(MessageImpl.BuilderImpl.serializer(), MessageImpl.BuilderImpl().apply(build))
        return kord.http.postEntity("channels/$id/messages", MessageImpl.serializer(), body)
    }

    override suspend fun triggerTyping(): Boolean =
        kord.http.post("channels/$id/typing")

    override suspend fun getPinnedMessages(): List<MessageImpl> =
        kord.http.getEntityList("channels/$id/pins", MessageImpl.serializer())

    override suspend fun bulkDelete(messages: List<Long>): Boolean =
        kord.http.post("channels/$id/messages/bulk-delete", TypedBody(BulkDeletion.serializer(), BulkDeletion(messages)))

    @Serializable
    data class BulkDeletion(
        val messages: List<Long>
    )
}
package pw.dotdash.kord.internal.entity.message

import io.ktor.client.request.parameter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import pw.dotdash.kord.api.entity.channel.TextChannel
import pw.dotdash.kord.api.entity.message.Embed
import pw.dotdash.kord.api.entity.message.Emoji
import pw.dotdash.kord.api.entity.message.Message
import pw.dotdash.kord.api.entity.message.MessageType
import pw.dotdash.kord.api.entity.user.User
import pw.dotdash.kord.internal.KordImpl
import pw.dotdash.kord.internal.entity.LazyEntity
import pw.dotdash.kord.internal.entity.guild.GuildImpl
import pw.dotdash.kord.internal.entity.guild.MemberImpl
import pw.dotdash.kord.internal.entity.user.UserImpl
import pw.dotdash.kord.internal.serial.OffsetDateTimeSerializer
import pw.dotdash.kord.internal.serial.StringBuilderSerializer
import java.time.OffsetDateTime

@Serializable
data class MessageImpl(
    override val id: Long,
    @SerialName("channel_id") override val channelId: Long,
    @SerialName("guild_id") override val guildId: Long? = null,
    override val author: UserImpl,
    override val content: String,
    @Serializable(OffsetDateTimeSerializer::class) override val timestamp: OffsetDateTime,
    @SerialName("edited_timestamp") @Serializable(OffsetDateTimeSerializer::class) override val editedTimestamp: OffsetDateTime?,
    override val tts: Boolean,
    @SerialName("mention_everyone") override val mentionEveryone: Boolean,
    @SerialName("mention_roles") override val roleMentions: List<Long>,
    override val attachments: List<AttachmentImpl>,
    override val embeds: List<EmbedImpl>,
    override val reactions: List<ReactionImpl> = emptyList(),
    override val nonce: Long? = null,
    override val pinned: Boolean,
    @SerialName("webhook_id") override val webhookId: Long? = null,
    @Serializable(MessageType.Companion::class) override val type: MessageType,
    override val activity: ActivityImpl? = null,
    override val application: ApplicationImpl? = null
) : Message, LazyEntity {
    @Transient
    override lateinit var kord: KordImpl

    @Transient
    override val edited: Boolean get() = editedTimestamp != null

    @Transient
    override val jumpUrl: String
        get() = "https://discordapp.com/channels/${guildId ?: "@me"}/$channelId/$id"

    override suspend fun init(kord: KordImpl) {
        this.kord = kord
    }

    override suspend fun channel(): TextChannel = kord.getGuildTextChannel(channelId)!!

    override suspend fun guild(): GuildImpl? = guildId?.let { kord.getGuild(it) }

    override suspend fun member(): MemberImpl? = guild()?.getMember(author.id)

    /**
     * FIXME - Timeout error
     */
    override suspend fun createReaction(emoji: Emoji): Boolean =
        kord.http.put("channels/$channelId/messages/$id/reactions/${emoji.pathValue}/@me")

    override suspend fun getReactions(emojiId: Long, before: Long?, after: Long?, limit: Int?): List<User> =
        kord.http.getEntityList("channels/$channelId/messages/$id/reactions/$emojiId", UserImpl.serializer()) {
            if (before != null) parameter("before", before)
            if (after != null) parameter("after", after)
            if (limit != null) parameter("limit", limit)
        }

    override suspend fun delete(): Boolean =
        kord.http.delete("channels/$channelId/messages/$id")

    override suspend fun pin(): Boolean = kord.http.put("channels/$channelId/pins/$id")

    override suspend fun unpin(): Boolean = kord.http.delete("channels/$channelId/pins/$id")

    @Serializable
    data class ActivityImpl(
        override val type: Int,
        @SerialName("party_id") override val partyId: String? = null
    ) : Message.Activity

    @Serializable
    data class ApplicationImpl(
        override val id: Long,
        @SerialName("cover_image") override val coverImage: String? = null,
        override val description: String,
        override val icon: String?,
        override val name: String
    ) : Message.Application

    @Serializable
    data class BuilderImpl(
        override var nonce: Long? = null,
        override var tts: Boolean = false
    ) : Message.Builder {

        @Serializable(StringBuilderSerializer::class) var content: StringBuilder? = null
        var embed: EmbedImpl.BuilderImpl? = null

        override fun content(build: StringBuilder.() -> Unit) {
            this.content = StringBuilder().apply(build)
        }

        override fun embed(build: Embed.Builder.() -> Unit) {
            this.embed = EmbedImpl.BuilderImpl().apply(build)
        }
    }
}
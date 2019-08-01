package pw.dotdash.kord.api.entity.message

import pw.dotdash.kord.api.Kord
import pw.dotdash.kord.api.entity.Entity
import pw.dotdash.kord.api.entity.Identifiable
import pw.dotdash.kord.api.entity.channel.TextChannel
import pw.dotdash.kord.api.entity.guild.Guild
import pw.dotdash.kord.api.entity.guild.Member
import pw.dotdash.kord.api.entity.user.User
import java.time.OffsetDateTime

interface Message : Entity, Identifiable {

    override val kord: Kord

    override val id: Long

    val channelId: Long

    val guildId: Long?

    val author: User

    val content: String

    val timestamp: OffsetDateTime

    val editedTimestamp: OffsetDateTime?

    val edited: Boolean

    val tts: Boolean

    val mentionEveryone: Boolean

    val roleMentions: List<Long>

    val attachments: List<Attachment>

    val embeds: List<Embed>

    val reactions: List<Reaction>

    val nonce: Long?

    val pinned: Boolean

    val webhookId: Long?

    val type: MessageType

    val activity: Activity?

    val application: Application?

    val jumpUrl: String

    suspend fun channel(): TextChannel

    suspend fun guild(): Guild?

    suspend fun member(): Member?

    suspend fun createReaction(emoji: Emoji): Boolean

    suspend fun getReactions(emojiId: Long, before: Long? = null, after: Long? = null, limit: Int? = null): List<User>

    suspend fun delete(): Boolean

    suspend fun pin(): Boolean

    suspend fun unpin(): Boolean

    interface Activity {

        val type: Int

        val partyId: String?
    }

    interface Application : Identifiable {

        override val id: Long

        val coverImage: String?

        val description: String

        val icon: String?

        val name: String
    }

    interface Builder {

        var nonce: Long?

        var tts: Boolean

        fun content(build: StringBuilder.() -> Unit)

        fun embed(build: Embed.Builder.() -> Unit)
    }
}
package pw.dotdash.kord.api.entity.channel

import pw.dotdash.kord.api.entity.message.Message
import java.time.OffsetDateTime

interface TextChannel : Channel {

    val lastMessageId: Long?

    val lastPinTimestamp: OffsetDateTime?

    suspend fun getMessages(around: Long? = null, before: Long? = null, after: Long? = null, limit: Int? = null): List<Message>

    suspend fun getMessage(id: Long) : Message?

    suspend fun createMessage(build: suspend Message.Builder.() -> Unit): Message

    suspend fun triggerTyping(): Boolean

    suspend fun getPinnedMessages(): List<Message>
}
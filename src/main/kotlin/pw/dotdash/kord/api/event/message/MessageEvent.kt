package pw.dotdash.kord.api.event.message

import pw.dotdash.kord.api.entity.message.Message
import pw.dotdash.kord.api.event.Event

interface MessageEvent : Event {

    val guildId: Long?

    val channelId: Long

    val messageId: Long

    val message: Message?
}
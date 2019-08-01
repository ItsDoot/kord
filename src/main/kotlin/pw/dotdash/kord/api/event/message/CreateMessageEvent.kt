package pw.dotdash.kord.api.event.message

import pw.dotdash.kord.api.entity.message.Message
import pw.dotdash.kord.api.entity.user.User

interface CreateMessageEvent : MessageEvent {

    override val message: Message

    val author: User get() = message.author
}
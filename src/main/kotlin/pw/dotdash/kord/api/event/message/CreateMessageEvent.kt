package pw.dotdash.kord.api.event.message

import pw.dotdash.kord.api.entity.message.Message

interface CreateMessageEvent : MessageEvent {

    override val message: Message
}
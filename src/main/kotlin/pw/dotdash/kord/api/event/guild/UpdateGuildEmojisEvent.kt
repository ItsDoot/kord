package pw.dotdash.kord.api.event.guild

import pw.dotdash.kord.api.entity.message.Emoji

interface UpdateGuildEmojisEvent : GuildEvent {

    val emojis: List<Emoji>
}
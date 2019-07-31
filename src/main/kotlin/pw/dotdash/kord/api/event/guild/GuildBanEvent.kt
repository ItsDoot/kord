package pw.dotdash.kord.api.event.guild

import pw.dotdash.kord.api.entity.user.User

interface GuildBanEvent : GuildEvent {

    val user: User
}
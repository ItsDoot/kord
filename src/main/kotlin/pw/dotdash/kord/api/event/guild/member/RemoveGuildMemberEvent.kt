package pw.dotdash.kord.api.event.guild.member

import pw.dotdash.kord.api.entity.user.User
import pw.dotdash.kord.api.event.guild.GuildEvent

interface RemoveGuildMemberEvent : GuildEvent {

    val user: User
}
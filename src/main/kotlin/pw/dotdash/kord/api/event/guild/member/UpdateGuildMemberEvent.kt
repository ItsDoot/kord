package pw.dotdash.kord.api.event.guild.member

import pw.dotdash.kord.api.entity.user.User
import pw.dotdash.kord.api.event.guild.GuildEvent

interface UpdateGuildMemberEvent : GuildEvent {

    val roleIds: List<Long>

    val user: User

    val nick: String
}
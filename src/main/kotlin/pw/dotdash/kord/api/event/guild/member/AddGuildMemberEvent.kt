package pw.dotdash.kord.api.event.guild.member

import pw.dotdash.kord.api.entity.guild.Member
import pw.dotdash.kord.api.event.guild.GuildEvent

interface AddGuildMemberEvent : GuildEvent {

    val member: Member
}
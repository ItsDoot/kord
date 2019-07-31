package pw.dotdash.kord.api.event.guild.member

import pw.dotdash.kord.api.entity.guild.Member
import pw.dotdash.kord.api.event.guild.GuildEvent

interface ChunkGuildMemberEvent : GuildEvent {

    val members: List<Member>
}
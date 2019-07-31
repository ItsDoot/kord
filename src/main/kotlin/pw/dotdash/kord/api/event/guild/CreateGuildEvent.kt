package pw.dotdash.kord.api.event.guild

import pw.dotdash.kord.api.entity.guild.Guild

interface CreateGuildEvent : GuildEvent {

    override suspend fun guild(): Guild
}
package pw.dotdash.kord.api.event.guild

import pw.dotdash.kord.api.entity.guild.Guild

interface UpdateGuildEvent : GuildEvent {

    override suspend fun guild(): Guild
}
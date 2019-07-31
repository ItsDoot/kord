package pw.dotdash.kord.api.event.guild

import pw.dotdash.kord.api.entity.guild.UnavailableGuild

interface DeleteGuildEvent : GuildEvent {

    override suspend fun guild(): UnavailableGuild
}
package pw.dotdash.kord.api.event.guild

import pw.dotdash.kord.api.Kord
import pw.dotdash.kord.api.entity.guild.GuildLike
import pw.dotdash.kord.api.event.Event

interface GuildEvent : Event {

    override val kord: Kord

    suspend fun guild(): GuildLike = kord.guildCache.get(guildId)!!

    val guildId: Long
}
package pw.dotdash.kord.api.event.guild

import pw.dotdash.kord.api.Kord
import pw.dotdash.kord.api.entity.guild.Guild
import pw.dotdash.kord.api.event.Event

interface CreateGuildEvent : Event {

    override val kord: Kord

    val guild: Guild
}
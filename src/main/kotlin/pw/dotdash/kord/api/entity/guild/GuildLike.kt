package pw.dotdash.kord.api.entity.guild

import pw.dotdash.kord.api.entity.Identifiable

interface GuildLike : Identifiable {

    override val id: Long

    val unavailable: Boolean
}
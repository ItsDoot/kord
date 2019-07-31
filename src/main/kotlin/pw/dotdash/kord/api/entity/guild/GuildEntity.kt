package pw.dotdash.kord.api.entity.guild

import pw.dotdash.kord.api.Kord
import pw.dotdash.kord.api.entity.Entity

interface GuildEntity : Entity {

    override val kord: Kord

    val guildId: Long

    suspend fun guild(): Guild = (kord.guildCache.get(guildId) as? Guild)!!
}
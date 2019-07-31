package pw.dotdash.kord.internal.entity.guild

import pw.dotdash.kord.api.entity.guild.GuildEntity
import pw.dotdash.kord.internal.KordImpl
import pw.dotdash.kord.internal.entity.LazyEntity

interface LazyGuildEntity : LazyEntity, GuildEntity {

    override var kord: KordImpl

    override var guildId: Long
}
package pw.dotdash.kord.api.entity.guild

interface UnavailableGuild : GuildLike {

    override val id: Long

    override val unavailable: Boolean get() = true
}
package pw.dotdash.kord.api.entity.channel

interface GuildChannel : Channel {

    val guildId: Long

    val position: Int

    val overwrites: List<Overwrite>

    val name: String

    val nsfw: Boolean

    val parentId: Long?
}
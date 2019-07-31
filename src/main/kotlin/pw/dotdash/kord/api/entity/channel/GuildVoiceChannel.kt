package pw.dotdash.kord.api.entity.channel

interface GuildVoiceChannel : GuildChannel {

    val bitrate: Int

    val userLimit: Int
}
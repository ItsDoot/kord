package pw.dotdash.kord.api.entity.guild

import pw.dotdash.kord.api.util.Option

interface GuildEmbed {

    val enabled: Boolean

    val channelId: Long?

    interface Editor {

        var enabled: Option<Boolean>

        var channelId: Option<Long?>
    }
}
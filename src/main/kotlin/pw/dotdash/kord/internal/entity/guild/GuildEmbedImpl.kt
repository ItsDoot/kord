package pw.dotdash.kord.internal.entity.guild

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import pw.dotdash.kord.api.entity.guild.GuildEmbed
import pw.dotdash.kord.api.util.None
import pw.dotdash.kord.api.util.Option

@Serializable
data class GuildEmbedImpl(
    override val enabled: Boolean,
    @SerialName("channel_id") override val channelId: Long?
) : GuildEmbed {

    @Serializable
    data class EditorImpl(
        override var enabled: Option<Boolean> = None,
        @SerialName("channel_id") override var channelId: Option<Long?> = None
    ) : GuildEmbed.Editor
}
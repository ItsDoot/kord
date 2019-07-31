package pw.dotdash.kord.internal.entity.channel

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import pw.dotdash.kord.api.entity.channel.ChannelType
import pw.dotdash.kord.internal.KordImpl
import pw.dotdash.kord.api.entity.channel.GuildVoiceChannel
import pw.dotdash.kord.internal.entity.LazyEntity

@Serializable
data class GuildVoiceChannelImpl(
    override val id: Long,
    @Serializable(ChannelType.Companion::class) override val type: ChannelType,
    @SerialName("guild_id") override val guildId: Long,
    override val position: Int,
    @SerialName("permission_overwrites") override val overwrites: List<OverwriteImpl>,
    override val name: String,
    override val nsfw: Boolean,
    @SerialName("parent_id") override val parentId: Long?,
    override val bitrate: Int,
    @SerialName("user_limit") override val userLimit: Int
) : GuildVoiceChannel, LazyEntity {
    @Transient
    override lateinit var kord: KordImpl

    override suspend fun init(kord: KordImpl) {
        this.kord = kord
    }
}
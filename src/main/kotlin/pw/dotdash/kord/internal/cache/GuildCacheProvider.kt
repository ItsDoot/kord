package pw.dotdash.kord.internal.cache

import pw.dotdash.kord.api.cache.MutableSnowflakeCache
import pw.dotdash.kord.internal.entity.channel.GuildTextChannelImpl
import pw.dotdash.kord.internal.entity.channel.GuildVoiceChannelImpl
import pw.dotdash.kord.internal.entity.guild.GuildImpl
import pw.dotdash.kord.internal.entity.guild.MemberImpl
import pw.dotdash.kord.internal.entity.guild.RoleImpl
import pw.dotdash.kord.internal.entity.message.EmojiImpl

data class GuildCacheProvider(
    val emojis: (GuildImpl) -> MutableSnowflakeCache<EmojiImpl>,
    val members: (GuildImpl) -> MutableSnowflakeCache<MemberImpl>,
    val roles: (GuildImpl) -> MutableSnowflakeCache<RoleImpl>,
    val textChannels: (GuildImpl) -> MutableSnowflakeCache<GuildTextChannelImpl>,
    val voiceChannels: (GuildImpl) -> MutableSnowflakeCache<GuildVoiceChannelImpl>
)
package pw.dotdash.kord.internal.cache

import pw.dotdash.kord.api.cache.MutableSnowflakeCache
import pw.dotdash.kord.api.cache.emptyCache
import pw.dotdash.kord.api.cache.simple.ConcurrentHashMapCache
import pw.dotdash.kord.internal.entity.channel.GuildTextChannelImpl
import pw.dotdash.kord.internal.entity.channel.GuildVoiceChannelImpl
import pw.dotdash.kord.internal.entity.guild.GuildLikeImpl
import pw.dotdash.kord.internal.entity.guild.MemberImpl
import pw.dotdash.kord.internal.entity.guild.RoleImpl
import pw.dotdash.kord.internal.entity.message.EmojiImpl
import pw.dotdash.kord.internal.entity.user.UserImpl

data class CacheProvider(
    val guildProvider: GuildCacheProvider,
    val guilds: () -> MutableSnowflakeCache<GuildLikeImpl>,
    val guildTextChannels: () -> MutableSnowflakeCache<GuildTextChannelImpl>,
    val guildVoiceChannels: () -> MutableSnowflakeCache<GuildVoiceChannelImpl>,
    val users: () -> MutableSnowflakeCache<UserImpl>
) {

    companion object {
        val Empty = CacheProvider(
            guildProvider = GuildCacheProvider(
                emojis = ::emptyCache,
                members = ::emptyCache,
                roles = ::emptyCache,
                textChannels = ::emptyCache,
                voiceChannels = ::emptyCache
            ),
            guilds = ::emptyCache,
            guildTextChannels = ::emptyCache,
            guildVoiceChannels = ::emptyCache,
            users = ::emptyCache
        )

        val ConcurrentHashMap = CacheProvider(
            guildProvider = GuildCacheProvider(
                emojis = ::ConcurrentHashMapCache,
                members = ::ConcurrentHashMapCache,
                roles = ::ConcurrentHashMapCache,
                textChannels = ::ConcurrentHashMapCache,
                voiceChannels = ::ConcurrentHashMapCache
            ),
            guilds = ::ConcurrentHashMapCache,
            guildTextChannels = ::ConcurrentHashMapCache,
            guildVoiceChannels = ::ConcurrentHashMapCache,
            users = ::ConcurrentHashMapCache
        )
    }
}

data class GuildCacheProvider(
    val emojis: () -> MutableSnowflakeCache<EmojiImpl>,
    val members: () -> MutableSnowflakeCache<MemberImpl>,
    val roles: () -> MutableSnowflakeCache<RoleImpl>,
    val textChannels: () -> MutableSnowflakeCache<GuildTextChannelImpl>,
    val voiceChannels: () -> MutableSnowflakeCache<GuildVoiceChannelImpl>
)
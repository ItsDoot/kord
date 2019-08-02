package pw.dotdash.kord.internal.cache

import pw.dotdash.kord.api.cache.MutableSnowflakeCache
import pw.dotdash.kord.internal.entity.channel.GuildTextChannelImpl
import pw.dotdash.kord.internal.entity.message.MessageImpl

data class ChannelCacheProvider(
    val guildMessages: (GuildTextChannelImpl) -> MutableSnowflakeCache<MessageImpl>
)
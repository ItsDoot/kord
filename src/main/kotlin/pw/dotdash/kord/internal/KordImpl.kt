package pw.dotdash.kord.internal

import io.ktor.client.HttpClient
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import pw.dotdash.kord.api.Kord
import pw.dotdash.kord.api.cache.MutableSnowflakeCache
import pw.dotdash.kord.api.cache.getOrFetch
import pw.dotdash.kord.api.entity.channel.GuildTextChannel
import pw.dotdash.kord.api.entity.channel.GuildVoiceChannel
import pw.dotdash.kord.api.entity.guild.Guild
import pw.dotdash.kord.api.entity.guild.Invite
import pw.dotdash.kord.api.entity.user.SelfUser
import pw.dotdash.kord.api.entity.user.User
import pw.dotdash.kord.api.entity.webhook.Webhook
import pw.dotdash.kord.api.event.Event
import pw.dotdash.kord.internal.cache.CacheProvider
import pw.dotdash.kord.internal.entity.channel.GuildTextChannelImpl
import pw.dotdash.kord.internal.entity.channel.GuildVoiceChannelImpl
import pw.dotdash.kord.internal.entity.guild.GuildImpl
import pw.dotdash.kord.internal.entity.guild.GuildLikeImpl
import pw.dotdash.kord.internal.entity.guild.InviteImpl
import pw.dotdash.kord.internal.entity.user.SelfUserImpl
import pw.dotdash.kord.internal.entity.user.UserImpl
import pw.dotdash.kord.internal.entity.webhook.WebhookImpl
import pw.dotdash.kord.internal.gateway.KordGateway
import pw.dotdash.kord.internal.http.KordHttpClient
import pw.dotdash.kord.internal.http.newHttpClient

@UseExperimental(ExperimentalCoroutinesApi::class, UnstableDefault::class)
class KordImpl constructor(
    override val token: String,
    val httpClient: HttpClient = newHttpClient(),
    val cacheProvider: CacheProvider = CacheProvider.ConcurrentHashMap,
    eventChannelProvider: () -> BroadcastChannel<Event> = ::ConflatedBroadcastChannel
) : Kord {

    companion object {
        val JSON = Json(JsonConfiguration(encodeDefaults = false, strictMode = false))
    }

    val http = KordHttpClient(this, token, httpClient)
    val gateway = KordGateway(this, token, httpClient)

    override val guildCache: MutableSnowflakeCache<GuildLikeImpl> = cacheProvider.guilds()
    override val guildTextChannelCache: MutableSnowflakeCache<GuildTextChannelImpl> = cacheProvider.guildTextChannels()
    override val guildVoiceChannelCache: MutableSnowflakeCache<GuildVoiceChannelImpl> = cacheProvider.guildVoiceChannels()
    override val userCache: MutableSnowflakeCache<UserImpl> = cacheProvider.users()

    override val events: BroadcastChannel<Event> = eventChannelProvider()

    override suspend fun connect() {
        if (gateway.connected) {
            throw IllegalStateException("Already connected to the gateway")
        }

        gateway.connect()
    }

    override suspend fun getGuild(id: Long): GuildImpl? {
        val cached = guildCache.get(id)

        if (cached == null || cached !is GuildImpl) {
            return http.getEntity("guilds/$id", GuildImpl.serializer())?.also {
                for (role in it.roles) {
                    role.init(this@KordImpl)
                }
            }
        }

        return cached
    }

    override suspend fun getGuildTextChannel(id: Long): GuildTextChannelImpl? =
        guildTextChannelCache.getOrFetch(id) {
            http.getEntity("channels/$id", GuildTextChannelImpl.serializer())
        }

    override suspend fun getGuildVoiceChannel(id: Long): GuildVoiceChannelImpl? =
        guildVoiceChannelCache.getOrFetch(id) {
            http.getEntity("channels/$id", GuildVoiceChannelImpl.serializer())
        }

    override suspend fun getInvite(code: String): Invite? =
        http.getEntity("invites/$code", InviteImpl.serializer())

    override suspend fun getCurrentUser(): SelfUserImpl =
        http.getEntity("users/@me", SelfUserImpl.serializer())!!

    override suspend fun getUser(id: Long): UserImpl? =
        userCache.getOrFetch(id) {
            http.getEntity("users/$id", UserImpl.serializer())
        }

    override suspend fun getWebhook(id: Long): Webhook? =
        http.getEntity("webhooks/$id", WebhookImpl.serializer())

    override suspend fun getWebhook(id: Long, token: String): Webhook? =
        http.getEntity("webhooks/$id/$token", WebhookImpl.serializer())
}
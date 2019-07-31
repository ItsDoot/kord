package pw.dotdash.kord.api

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filterIsInstance
import pw.dotdash.kord.api.cache.SnowflakeCache
import pw.dotdash.kord.api.entity.channel.GuildTextChannel
import pw.dotdash.kord.api.entity.channel.GuildVoiceChannel
import pw.dotdash.kord.api.entity.guild.Guild
import pw.dotdash.kord.api.entity.guild.GuildLike
import pw.dotdash.kord.api.entity.guild.Invite
import pw.dotdash.kord.api.entity.user.SelfUser
import pw.dotdash.kord.api.entity.user.User
import pw.dotdash.kord.api.entity.webhook.Webhook
import pw.dotdash.kord.api.event.Event
import pw.dotdash.kord.internal.KordImpl

@UseExperimental(ExperimentalCoroutinesApi::class)
interface Kord {

    companion object {
        operator fun invoke(token: String) : Kord = KordImpl(token)
    }

    val token: String

    val guildCache: SnowflakeCache<GuildLike>

    val guildTextChannelCache: SnowflakeCache<GuildTextChannel>

    val guildVoiceChannelCache: SnowflakeCache<GuildVoiceChannel>

    val userCache: SnowflakeCache<User>

    val events: BroadcastChannel<Event>

    suspend fun connect()

    suspend fun getGuild(id: Long): Guild?

    suspend fun getGuildTextChannel(id: Long): GuildTextChannel?

    suspend fun getGuildVoiceChannel(id: Long): GuildVoiceChannel?

    suspend fun getInvite(code: String): Invite?

    suspend fun getCurrentUser(): SelfUser

    suspend fun getUser(id: Long): User?

    suspend fun getWebhook(id: Long): Webhook?

    suspend fun getWebhook(id: Long, token: String): Webhook?
}

@UseExperimental(FlowPreview::class)
inline fun <reified T : Event> Kord.eventFlow(): Flow<T> = this.events.asFlow().filterIsInstance()
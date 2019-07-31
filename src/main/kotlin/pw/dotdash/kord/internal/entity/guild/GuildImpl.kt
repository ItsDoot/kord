package pw.dotdash.kord.internal.entity.guild

import io.ktor.client.request.parameter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import pw.dotdash.kord.api.cache.MutableSnowflakeCache
import pw.dotdash.kord.api.entity.guild.*
import pw.dotdash.kord.internal.KordImpl
import pw.dotdash.kord.internal.cache.GuildCacheProvider
import pw.dotdash.kord.internal.entity.LazyEntity
import pw.dotdash.kord.internal.entity.channel.GuildTextChannelImpl
import pw.dotdash.kord.internal.entity.channel.GuildVoiceChannelImpl
import pw.dotdash.kord.internal.entity.message.EmojiImpl
import pw.dotdash.kord.internal.http.TypedBody

sealed class GuildLikeImpl : GuildLike

@Serializable
data class UnavailableGuildImpl(
    override val id: Long,
    override val unavailable: Boolean = true
) : GuildLikeImpl(), UnavailableGuild

@Serializable
data class GuildImpl(
    override val id: Long,
    override val name: String,
    override val icon: String?,
    override val splash: String?,
    override val owner: Boolean = false,
    @SerialName("owner_id") override val ownerId: Long,
    override val permissions: PermissionSet = PermissionSet(),
    override val region: String,
    @SerialName("afk_channel_id") override val afkChannelId: Long?,
    @SerialName("afk_timeout") override val afkTimeout: Int,
    @SerialName("embed_enabled") override val embedEnabled: Boolean = false,
    @SerialName("embed_channel_id") override val embedChannelId: Long? = null,
    @SerialName("verification_level") override val verificationLevel: Int,
    @SerialName("default_message_notifications") override val defaultMessageNotifications: Int,
    @SerialName("explicit_content_filter") override val explicitContentFilter: Int,
    override val roles: List<RoleImpl>,
    override val emojis: List<EmojiImpl>,
    override val features: Set<String>,
    @SerialName("mfa_level") override val mfaLevel: Int,
    @SerialName("application_id") override val applicationId: Long?,
    @SerialName("widget_enabled") override val widgetEnabled: Boolean = false,
    @SerialName("widget_channel_id") override val widgetChannelId: Long? = null,
    @SerialName("system_channel_id") override val systemChannelId: Long? = null,
    @SerialName("max_presences") override val maxPresences: Int? = null,
    @SerialName("max_members") override val maxMembers: Int? = null,
    @SerialName("vanity_url_code") override val vanityUrlCode: String?,
    override val description: String?,
    override val banner: String?,
    @SerialName("premium_tier") override val premiumTier: Int,
    @SerialName("premium_subscription_count") override val premiumSubscriptionCount: Int? = null
) : GuildLikeImpl(), Guild, LazyEntity {
    @Transient
    override lateinit var kord: KordImpl

    @Transient
    override lateinit var emojiCache: MutableSnowflakeCache<EmojiImpl>

    @Transient
    override lateinit var memberCache: MutableSnowflakeCache<MemberImpl>

    @Transient
    override lateinit var roleCache: MutableSnowflakeCache<RoleImpl>

    @Transient
    override lateinit var textChannelCache: MutableSnowflakeCache<GuildTextChannelImpl>

    @Transient
    override lateinit var voiceChannelCache: MutableSnowflakeCache<GuildVoiceChannelImpl>

    override suspend fun init(kord: KordImpl) {
        this.kord = kord
    }

    fun initCaches(provider: GuildCacheProvider) {
        this.emojiCache = provider.emojis()
        this.memberCache = provider.members()
        this.roleCache = provider.roles()
        this.textChannelCache = provider.textChannels()
        this.voiceChannelCache = provider.voiceChannels()
    }

    override suspend fun getMember(id: Long): MemberImpl? =
        kord.http.getEntity("guilds/${this.id}/members/$id", MemberImpl.serializer())

    override suspend fun listMembers(limit: Int?, after: Long?): List<MemberImpl> =
        kord.http.getEntityList("guilds/$id/members", MemberImpl.serializer()) {
            if (limit != null) parameter("limit", limit)
            if (after != null) parameter("after", after)
        }

    override suspend fun getBans(): List<BanImpl> =
        kord.http.getList("guilds/$id/bans", BanImpl.serializer())

    override suspend fun getBan(userId: Long): Ban? =
        kord.http.get("guilds/$id/$userId", BanImpl.serializer())

    override suspend fun getRoles(): List<RoleImpl> =
        kord.http.getGuildEntityList("guilds/$id/roles", RoleImpl.serializer(), guildId = this.id)

    override suspend fun createRole(block: suspend Role.Builder.() -> Unit): Role {
        val body = TypedBody(
            RoleImpl.BuilderImpl.serializer(),
            RoleImpl.BuilderImpl().apply { block() }
        )

        return kord.http.postGuildEntity("guilds/$id/roles", RoleImpl.serializer(), body, guildId = this.id)!!
    }

    override suspend fun getVoiceRegions(): List<VoiceRegionImpl> =
        kord.http.getList("guilds/$id/regions", VoiceRegionImpl.serializer())

    override suspend fun getEmbed(): GuildEmbedImpl =
        kord.http.get("guilds/$id/embed", GuildEmbedImpl.serializer())!!

    override suspend fun editEmbed(block: suspend GuildEmbed.Editor.() -> Unit): GuildEmbedImpl {
        val body = TypedBody(
            GuildEmbedImpl.EditorImpl.serializer(),
            GuildEmbedImpl.EditorImpl().apply { block() }
        )

        return kord.http.patch("guilds/$id/embed", GuildEmbedImpl.serializer(), body)!!
    }
}
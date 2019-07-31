package pw.dotdash.kord.api.entity.guild

import pw.dotdash.kord.api.Kord
import pw.dotdash.kord.api.cache.SnowflakeCache
import pw.dotdash.kord.api.entity.Entity
import pw.dotdash.kord.api.entity.Identifiable
import pw.dotdash.kord.api.entity.channel.GuildTextChannel
import pw.dotdash.kord.api.entity.channel.GuildVoiceChannel
import pw.dotdash.kord.api.entity.message.Emoji

interface Guild : GuildLike, Entity, Identifiable {

    override val kord: Kord

    override val id: Long

    val name: String

    val icon: String?

    val splash: String?

    val owner: Boolean

    val ownerId: Long

    val permissions: PermissionSet

    val region: String

    val afkChannelId: Long?

    val afkTimeout: Int

    val embedEnabled: Boolean

    val embedChannelId: Long?

    val verificationLevel: Int

    val defaultMessageNotifications: Int

    val explicitContentFilter: Int

    val roles: List<Role>

    val emojis: List<Emoji>

    val features: Set<String>

    val mfaLevel: Int

    val applicationId: Long?

    val widgetEnabled: Boolean

    val widgetChannelId: Long?

    val systemChannelId: Long?

    override val unavailable: Boolean get() = false

    val maxPresences: Int?

    val maxMembers: Int?

    val vanityUrlCode: String?

    val description: String?

    val banner: String?

    val premiumTier: Int

    val premiumSubscriptionCount: Int?

    val emojiCache: SnowflakeCache<Emoji>

    val memberCache: SnowflakeCache<Member>

    val roleCache: SnowflakeCache<Role>

    val textChannelCache: SnowflakeCache<GuildTextChannel>

    val voiceChannelCache: SnowflakeCache<GuildVoiceChannel>

    suspend fun getMember(id: Long): Member?

    suspend fun listMembers(limit: Int? = null, after: Long? = null): List<Member>

    suspend fun getBans(): List<Ban>

    suspend fun getBan(userId: Long): Ban?

    suspend fun getRoles(): List<Role>

    suspend fun createRole(block: suspend Role.Builder.() -> Unit): Role

    suspend fun getVoiceRegions(): List<VoiceRegion>

    suspend fun getEmbed(): GuildEmbed

    suspend fun editEmbed(block: suspend GuildEmbed.Editor.() -> Unit): GuildEmbed

    interface Builder {

        var name: String

        var region: String

        var icon: String

        var verificationLevel: VerificationLevel

        var defaultNotificationLevel: NotificationLevel

        var contentFilterLevel: ContentFilterLevel

        fun role()
    }
}
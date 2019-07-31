package pw.dotdash.kord.internal.entity.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.list
import pw.dotdash.kord.api.entity.user.SelfUser
import pw.dotdash.kord.api.util.None
import pw.dotdash.kord.api.util.Option
import pw.dotdash.kord.internal.KordImpl
import pw.dotdash.kord.internal.entity.LazyEntity

@Serializable
data class SelfUserImpl(
    override val id: Long,
    override val username: String,
    override val discriminator: String,
    override val avatar: String?,
    override val bot: Boolean? = null,
    @SerialName("mfa_enabled") override val mfaEnabled: Boolean? = null,
    override val locale: String? = null,
    override val flags: Int? = null,
    @SerialName("premium_type") override val premiumType: Int? = null,
    override val verified: Boolean,
    override val email: String?
) : SelfUser, LazyEntity {
    @Transient
    override lateinit var kord: KordImpl

    override suspend fun init(kord: KordImpl) {
        this.kord = kord
    }

    @Serializable
    data class GuildImpl(
        override val id: Long,
        override val name: String,
        override val icon: String?,
        override val owner: Boolean,
        override val permissions: Int
    ) : SelfUser.Guild

    override suspend fun edit(block: SelfUser.Editor.() -> Unit): SelfUser {
        TODO()
    }

    override suspend fun getGuilds(): List<SelfUser.Guild> =
        kord.http.get("users/@me/guilds", GuildImpl.serializer().list)!!

    @Serializable
    data class EditorImpl(
        override var username: Option<String> = None,
        override var avatar: Option<String?> = None
    ) : SelfUser.Editor
}
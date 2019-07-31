package pw.dotdash.kord.internal.entity.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import pw.dotdash.kord.internal.KordImpl
import pw.dotdash.kord.api.entity.user.User
import pw.dotdash.kord.internal.entity.LazyEntity

@Serializable
data class UserImpl(
    override val id: Long,
    override val username: String,
    override val discriminator: String,
    override val avatar: String?,
    override val bot: Boolean? = null,
    @SerialName("mfa_enabled") override val mfaEnabled: Boolean? = null,
    override val locale: String? = null,
    override val flags: Int? = null,
    @SerialName("premium_type") override val premiumType: Int? = null
) : User, LazyEntity {
    @Transient
    override lateinit var kord: KordImpl

    override suspend fun init(kord: KordImpl) {
        this.kord = kord
    }
}
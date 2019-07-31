package pw.dotdash.kord.internal.entity.guild

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import pw.dotdash.kord.api.entity.guild.Integration
import pw.dotdash.kord.internal.entity.user.UserImpl
import pw.dotdash.kord.internal.serial.OffsetDateTimeSerializer
import java.time.OffsetDateTime

@Serializable
data class IntegrationImpl(
    override val id: Long,
    override val name: String,
    override val type: String,
    override val enabled: Boolean,
    override val syncing: Boolean,
    @SerialName("role_id") override val roleId: Long,
    @SerialName("expire_behavior") override val expireBehavior: Int,
    @SerialName("expire_grace_period") override val expireGracePeriod: Int,
    override val user: UserImpl,
    override val account: AccountImpl,
    @SerialName("synced_at") @Serializable(OffsetDateTimeSerializer::class) override val syncedAt: OffsetDateTime
) : Integration {

    @Serializable
    data class AccountImpl(
        override val id: String,
        override val name: String
    ) : Integration.Account
}
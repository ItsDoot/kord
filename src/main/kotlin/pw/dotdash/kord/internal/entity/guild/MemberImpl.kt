package pw.dotdash.kord.internal.entity.guild

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import pw.dotdash.kord.api.entity.guild.Member
import pw.dotdash.kord.internal.KordImpl
import pw.dotdash.kord.internal.entity.LazyEntity
import pw.dotdash.kord.internal.entity.user.UserImpl
import pw.dotdash.kord.internal.serial.OffsetDateTimeSerializer
import java.time.OffsetDateTime

@Serializable
data class MemberImpl(
    override val user: UserImpl,
    override val nick: String? = null,
    @SerialName("roles") override val roleIds: ArrayList<Long>,
    @SerialName("joined_at") @Serializable(OffsetDateTimeSerializer::class) override val joinedAt: OffsetDateTime,
    @SerialName("premium_since") @Serializable(OffsetDateTimeSerializer::class) override val premiumSince: OffsetDateTime?,
    override val deaf: Boolean,
    override val mute: Boolean
) : Member, LazyEntity {
    @Transient
    override lateinit var kord: KordImpl

    override suspend fun init(kord: KordImpl) {
        this.kord = kord
    }

    override suspend fun edit(block: Member.Editor.() -> Unit): Boolean {
        TODO()
    }

    override suspend fun plusAssign(roleId: Long) {
        TODO()
    }

    override suspend fun minusAssign(roleId: Long) {
        TODO()
    }
}
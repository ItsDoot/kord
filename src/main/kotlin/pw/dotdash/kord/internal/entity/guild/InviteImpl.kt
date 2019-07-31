package pw.dotdash.kord.internal.entity.guild

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import pw.dotdash.kord.api.entity.guild.Invite
import pw.dotdash.kord.internal.KordImpl
import pw.dotdash.kord.internal.entity.LazyEntity
import pw.dotdash.kord.internal.entity.user.UserImpl

@Serializable
data class InviteImpl(
    override val code: String,
    override val guild: GuildImpl? = null,
    override val channel: ChannelImpl,
    override val targetUser: UserImpl,
    override val targetUserType: Int? = null,
    override val approximatePresenceCount: Int? = null,
    override val approximateMemberCount: Int? = null
) : Invite, LazyEntity {
    @Transient
    override lateinit var kord: KordImpl

    override suspend fun init(kord: KordImpl) {
        this.kord = kord
    }

    override suspend fun delete() {
        kord.http.delete("invites/$code")
    }

    @Serializable
    data class GuildImpl(
        override val id: Long,
        override val name: String,
        override val splash: String?,
        override val icon: String?
    ) : Invite.Guild

    @Serializable
    data class ChannelImpl(
        override val id: Long,
        override val name: String,
        override val type: Int
    ) : Invite.Channel
}
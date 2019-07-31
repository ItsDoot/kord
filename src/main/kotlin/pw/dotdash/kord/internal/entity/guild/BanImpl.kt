package pw.dotdash.kord.internal.entity.guild

import kotlinx.serialization.Serializable
import pw.dotdash.kord.api.entity.guild.Ban
import pw.dotdash.kord.internal.entity.user.UserImpl

@Serializable
data class BanImpl(
    override val reason: String?,
    override val user: UserImpl
) : Ban
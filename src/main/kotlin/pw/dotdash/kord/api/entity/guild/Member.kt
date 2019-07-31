package pw.dotdash.kord.api.entity.guild

import pw.dotdash.kord.api.Kord
import pw.dotdash.kord.api.entity.Entity
import pw.dotdash.kord.api.entity.user.User
import pw.dotdash.kord.api.util.Option
import java.time.OffsetDateTime

interface Member : Entity {

    override val kord: Kord

    val user: User

    val nick: String?

    val roleIds: List<Long>

    val joinedAt: OffsetDateTime

    val premiumSince: OffsetDateTime?

    val deaf: Boolean

    val mute: Boolean

    suspend fun edit(block: Editor.() -> Unit): Boolean

    suspend operator fun plusAssign(roleId: Long)

    suspend operator fun plusAssign(role: Role) = plusAssign(role.id)

    suspend operator fun minusAssign(roleId: Long)

    suspend operator fun minusAssign(role: Role) = plusAssign(role.id)

    interface Editor {

        var nick: Option<String>

        var roles: Option<Set<Long>>

        var mute: Option<Boolean>

        var deaf: Option<Boolean>

        var channelId: Option<Long?>
    }
}
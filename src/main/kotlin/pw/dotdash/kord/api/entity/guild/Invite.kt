package pw.dotdash.kord.api.entity.guild

import pw.dotdash.kord.api.Kord
import pw.dotdash.kord.api.entity.Entity
import pw.dotdash.kord.api.entity.Identifiable
import pw.dotdash.kord.api.entity.user.User

interface Invite : Entity {

    override val kord: Kord

    val code: String

    val guild: Guild?

    val channel: Channel

    val targetUser: User

    val targetUserType: Int?

    val approximatePresenceCount: Int?

    val approximateMemberCount: Int?

    suspend fun delete()

    interface Guild : Identifiable {

        override val id: Long

        val name: String

        val splash: String?

        val icon: String?
    }

    interface Channel : Identifiable {

        override val id: Long

        val name: String

        val type: Int
    }
}
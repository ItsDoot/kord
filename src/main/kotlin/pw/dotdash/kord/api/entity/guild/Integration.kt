package pw.dotdash.kord.api.entity.guild

import pw.dotdash.kord.api.entity.Identifiable
import pw.dotdash.kord.api.entity.user.User
import java.time.OffsetDateTime

interface Integration : Identifiable {

    override val id: Long

    val name: String

    val type: String

    val enabled: Boolean

    val syncing: Boolean

    val roleId: Long

    val expireBehavior: Int

    val expireGracePeriod: Int

    val user: User

    val account: Account

    val syncedAt: OffsetDateTime

    interface Account {

        val id: String

        val name: String
    }
}
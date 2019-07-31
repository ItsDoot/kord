package pw.dotdash.kord.api.entity.webhook

import pw.dotdash.kord.api.entity.Entity
import pw.dotdash.kord.api.entity.Identifiable
import pw.dotdash.kord.api.entity.user.User

interface Webhook : Entity, Identifiable {

    override val id: Long

    val guildId: Long?

    val channelId: Long

    val user: User?

    val name: String?

    val avatar: String?

    val token: String
}
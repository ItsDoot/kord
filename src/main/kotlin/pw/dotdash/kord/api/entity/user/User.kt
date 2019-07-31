package pw.dotdash.kord.api.entity.user

import pw.dotdash.kord.internal.KordImpl
import pw.dotdash.kord.api.entity.Entity
import pw.dotdash.kord.api.entity.Identifiable
import pw.dotdash.kord.api.entity.Mentionable

interface User : Entity, Identifiable, Mentionable {

    override val kord: KordImpl

    override val id: Long

    val username: String

    val discriminator: String

    val avatar: String?

    val bot: Boolean?

    val mfaEnabled: Boolean?

    val locale: String?

    val flags: Int?

    val premiumType: Int?

    override fun asMention(): String = "<@$id>"
}
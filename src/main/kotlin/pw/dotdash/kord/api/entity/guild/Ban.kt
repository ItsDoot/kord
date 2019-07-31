package pw.dotdash.kord.api.entity.guild

import pw.dotdash.kord.api.entity.user.User

interface Ban {

    val reason: String?

    val user: User
}
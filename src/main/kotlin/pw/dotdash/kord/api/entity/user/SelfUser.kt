package pw.dotdash.kord.api.entity.user

import pw.dotdash.kord.api.entity.Identifiable
import pw.dotdash.kord.api.util.Option

interface SelfUser : User {

    val verified: Boolean

    val email: String?

    suspend fun edit(block: Editor.() -> Unit) : SelfUser

    suspend fun getGuilds(): List<Guild>

    interface Guild : Identifiable {

        override val id: Long

        val name: String

        val icon: String?

        val owner: Boolean

        val permissions: Int
    }

    interface Editor {

        var username: Option<String>

        var avatar: Option<String?>
    }
}
package pw.dotdash.kord.api.entity.user

interface UserConnection {

    val id: String

    val name: String

    val type: String

    val revoked: Boolean

    // TODO integrations

    val verified: Boolean

    val friendSync: Boolean

    val showActivity: Boolean

    val visibility: Int
}
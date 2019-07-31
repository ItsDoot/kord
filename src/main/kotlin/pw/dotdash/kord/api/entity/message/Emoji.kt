package pw.dotdash.kord.api.entity.message

import pw.dotdash.kord.api.entity.Mentionable
import pw.dotdash.kord.api.entity.user.User
import pw.dotdash.kord.internal.entity.message.EmojiImpl

interface Emoji: Mentionable {

    companion object {

        fun unicode(unicode: String): Emoji = EmojiImpl.unicode(unicode)
    }

    val id: Long?

    val name: String

    val roles: List<Long>

    val user: User?

    val requiresColons: Boolean?

    val managed: Boolean?

    val animated: Boolean?

    override fun asMention(): String =
        if (animated == true) {
            "<a:$name:$id>"
        } else {
            "<:$name:$id>"
        }
}
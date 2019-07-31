package pw.dotdash.kord.internal.entity.message

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import pw.dotdash.kord.api.entity.message.Emoji
import pw.dotdash.kord.api.entity.user.User

@Serializable
data class EmojiImpl(
    override val id: Long?,
    override val name: String,
    override val roles: List<Long> = emptyList(),
    override val user: User? = null,
    @SerialName("require_colons") override val requiresColons: Boolean? = null,
    override val managed: Boolean? = null,
    override val animated: Boolean? = null
) : Emoji {

    companion object {
        fun unicode(unicode: String): EmojiImpl =
            EmojiImpl(null, unicode)
    }
}

val Emoji.pathValue: String get() = if (id != null) "$name:$id" else name
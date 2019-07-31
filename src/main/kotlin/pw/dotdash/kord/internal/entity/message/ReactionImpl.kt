package pw.dotdash.kord.internal.entity.message

import kotlinx.serialization.Serializable
import pw.dotdash.kord.api.entity.message.Reaction

@Serializable
data class ReactionImpl(
    override val count: Int,
    override val me: Boolean,
    override val emoji: EmojiImpl
) : Reaction
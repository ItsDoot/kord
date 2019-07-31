package pw.dotdash.kord.api.entity.message

interface Reaction {

    val count: Int

    val me: Boolean

    val emoji: Emoji
}
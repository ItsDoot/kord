package pw.dotdash.kord.api.entity.guild

interface VoiceRegion {

    val id: String

    val name: String

    val vip: Boolean

    val optimal: Boolean

    val deprecated: Boolean

    val custom: Boolean
}
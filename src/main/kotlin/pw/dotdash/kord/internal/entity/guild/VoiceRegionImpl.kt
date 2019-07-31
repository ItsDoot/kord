package pw.dotdash.kord.internal.entity.guild

import kotlinx.serialization.Serializable
import pw.dotdash.kord.api.entity.guild.VoiceRegion

@Serializable
data class VoiceRegionImpl(
    override val id: String,
    override val name: String,
    override val vip: Boolean,
    override val optimal: Boolean,
    override val deprecated: Boolean,
    override val custom: Boolean
) : VoiceRegion
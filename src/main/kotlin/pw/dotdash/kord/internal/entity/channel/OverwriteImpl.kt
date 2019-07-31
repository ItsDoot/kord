package pw.dotdash.kord.internal.entity.channel

import kotlinx.serialization.Serializable
import pw.dotdash.kord.api.entity.channel.Overwrite
import pw.dotdash.kord.api.entity.guild.PermissionSet

@Serializable
data class OverwriteImpl(
    override val id: Long,
    override val type: String,
    override val allow: PermissionSet,
    override val deny: PermissionSet
) : Overwrite
package pw.dotdash.kord.api.entity.channel

import pw.dotdash.kord.api.entity.Identifiable
import pw.dotdash.kord.api.entity.guild.PermissionSet

interface Overwrite : Identifiable {

    override val id: Long

    val type: String

    val allow: PermissionSet

    val deny: PermissionSet
}
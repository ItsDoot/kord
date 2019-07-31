package pw.dotdash.kord.api.entity.guild

import pw.dotdash.kord.api.Kord
import pw.dotdash.kord.api.entity.Identifiable
import pw.dotdash.kord.api.entity.Mentionable
import pw.dotdash.kord.api.util.Option
import pw.dotdash.kord.api.util.Rgb

interface Role : GuildEntity, Identifiable, Mentionable, Comparable<Role> {

    override val kord: Kord

    override val guildId: Long

    override val id: Long

    val name: String

    val color: Rgb

    val hoist: Boolean

    val position: Int

    val permissions: PermissionSet

    val managed: Boolean

    val mentionable: Boolean

    override fun asMention(): String = "<@&$id>"

    override fun compareTo(other: Role): Int = this.position.compareTo(other.position)

    suspend fun edit(block: suspend Editor.() -> Unit): Role

    suspend fun delete(): Boolean

    interface Builder {

        var name: String

        var permissions: Option<PermissionSet>

        var color: Rgb

        var hoist: Boolean

        var mentionable: Boolean
    }

    interface Editor {

        var name: Option<String>

        var permissions: Option<PermissionSet>

        var color: Option<Rgb>

        var hoist: Option<Boolean>

        var mentionable: Option<Boolean>
    }
}
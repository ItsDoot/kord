package pw.dotdash.kord.internal.entity.guild

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import pw.dotdash.kord.api.entity.guild.PermissionSet
import pw.dotdash.kord.api.entity.guild.Role
import pw.dotdash.kord.api.util.None
import pw.dotdash.kord.api.util.Option
import pw.dotdash.kord.api.util.Rgb
import pw.dotdash.kord.internal.KordImpl
import pw.dotdash.kord.internal.http.TypedBody

@Serializable
data class RoleImpl(
    override val id: Long,
    override val name: String,
    override val color: Rgb,
    override val hoist: Boolean,
    override val position: Int,
    override val permissions: PermissionSet,
    override val managed: Boolean,
    override val mentionable: Boolean
) : Role, LazyGuildEntity {
    @Transient
    override lateinit var kord: KordImpl
    @Transient
    override var guildId: Long = 0

    override suspend fun init(kord: KordImpl) {
        this.kord = kord
    }

    override suspend fun edit(block: suspend Role.Editor.() -> Unit): Role {
        val body = TypedBody(
            EditorImpl.serializer(),
            EditorImpl().apply { block() }
        )

        return kord.http.patch("guilds/$guildId/roles/$id", serializer(), body)!!
    }

    override suspend fun delete(): Boolean {
        TODO()
    }

    @Serializable
    data class BuilderImpl(
        override var name: String = "new role",
        override var permissions: Option<PermissionSet> = None,
        override var color: Rgb = Rgb.ZERO,
        override var hoist: Boolean = false,
        override var mentionable: Boolean = false
    ) : Role.Builder

    @Serializable
    data class EditorImpl(
        override var name: Option<String> = None,
        override var permissions: Option<PermissionSet> = None,
        override var color: Option<Rgb> = None,
        override var hoist: Option<Boolean> = None,
        override var mentionable: Option<Boolean> = None
    ) : Role.Editor
}
package pw.dotdash.kord.api.entity.guild

import kotlinx.serialization.*
import kotlinx.serialization.internal.IntDescriptor
import java.util.*

@Serializable
class PermissionSet(private val permissions: EnumSet<Permission>) : MutableSet<Permission> by permissions {
    constructor() : this(EnumSet.noneOf(Permission::class.java))
    constructor(vararg permissions: Permission) : this(EnumSet.noneOf(Permission::class.java).apply { addAll(permissions) })

    constructor(permissions: Long) : this() {
        if (permissions == 0L) return

        Permission.values().filterTo(this) { permissions and it.raw == it.raw }
    }

    val raw: Long get() = raw(this)

    override fun toString(): String = permissions.toString()

    @Serializer(PermissionSet::class)
    companion object : KSerializer<PermissionSet> {
        fun raw(vararg permissions: Permission): Long =
            raw(permissions.toList())

        fun raw(permissions: Iterable<Permission>): Long =
            permissions.fold(0L) { acc, permission -> acc or permission.raw }

        override val descriptor: SerialDescriptor = IntDescriptor.withName("PermissionSet")

        override fun deserialize(decoder: Decoder): PermissionSet {
            return PermissionSet(decoder.decodeLong())
        }

        override fun serialize(encoder: Encoder, obj: PermissionSet) {
            encoder.encodeLong(obj.raw)
        }
    }
}
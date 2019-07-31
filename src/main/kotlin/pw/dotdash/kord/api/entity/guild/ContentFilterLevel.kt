package pw.dotdash.kord.api.entity.guild

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import pw.dotdash.kord.internal.serial.ordinalEnumSerializer

@Serializable
enum class ContentFilterLevel {
    DISABLED,
    MEMBERS_WITHOUT_ROLES,
    ALL_MEMBERS,
    UNKNOWN;

    @Serializer(ContentFilterLevel::class)
    companion object : KSerializer<ContentFilterLevel> by ordinalEnumSerializer(UNKNOWN)
}
package pw.dotdash.kord.api.entity.guild

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import pw.dotdash.kord.internal.serial.ordinalEnumSerializer

@Serializable
enum class MFALevel {
    NONE,
    ELEVATED,
    UNKNOWN;

    @Serializer(MFALevel::class)
    companion object : KSerializer<MFALevel> by ordinalEnumSerializer(UNKNOWN)
}
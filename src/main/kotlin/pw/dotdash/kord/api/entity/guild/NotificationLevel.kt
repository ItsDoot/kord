package pw.dotdash.kord.api.entity.guild

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import pw.dotdash.kord.internal.serial.ordinalEnumSerializer

@Serializable
enum class NotificationLevel {
    ALL_MESSAGES,
    ONLY_MENTIONS,
    UNKNOWN;

    @Serializer(NotificationLevel::class)
    companion object : KSerializer<NotificationLevel> by ordinalEnumSerializer(UNKNOWN)
}
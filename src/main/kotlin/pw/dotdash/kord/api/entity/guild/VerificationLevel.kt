package pw.dotdash.kord.api.entity.guild

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import pw.dotdash.kord.internal.serial.ordinalEnumSerializer

/**
 * The verification level of a Guild determines when a new member will be able to speak.
 */
@Serializable
enum class VerificationLevel {
    /**
     * No requirement to speak.
     */
    NONE,

    /**
     * Requires the member's account to have a verified email.
     */
    LOW,

    /**
     * Requires the member's account to be at least 5 minutes old.
     */
    MEDIUM,

    /**
     * Requires the member to be in the Guild for at least 10 minutes.
     */
    HIGH,

    /**
     * Requires the member's account to have a verified phone.
     */
    VERY_HIGH,

    /**
     * An unknown verification level.
     */
    UNKNOWN;

    @Serializer(VerificationLevel::class)
    companion object : KSerializer<VerificationLevel> by ordinalEnumSerializer(UNKNOWN)
}
package pw.dotdash.kord.api.entity.guild

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import pw.dotdash.kord.internal.serial.ordinalEnumSerializer

@Serializable
enum class PremiumTier {
    NONE,
    TIER_1,
    TIER_2,
    TIER_3,
    UNKNOWN;

    @Serializer(PremiumTier::class)
    companion object : KSerializer<PremiumTier> by ordinalEnumSerializer(UNKNOWN)
}
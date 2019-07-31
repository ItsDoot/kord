package pw.dotdash.kord.api.entity.channel

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import pw.dotdash.kord.internal.serial.ordinalEnumSerializer

@Serializable
enum class ChannelType {
    GUILD_TEXT,
    DM,
    GUILD_VOICE,
    GROUP_DM,
    GUILD_CATEGORY,
    GUILD_NEWS,
    GUILD_STORE;

    @Serializer(ChannelType::class)
    companion object : KSerializer<ChannelType> by ordinalEnumSerializer()
}
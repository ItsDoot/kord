package pw.dotdash.kord.internal.serial

import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor
import java.time.OffsetDateTime

@Serializer(OffsetDateTime::class)
object OffsetDateTimeSerializer : KSerializer<OffsetDateTime> {

    override val descriptor: SerialDescriptor = StringDescriptor

    override fun deserialize(decoder: Decoder): OffsetDateTime =
        OffsetDateTime.parse(decoder.decodeString())

    override fun serialize(encoder: Encoder, obj: OffsetDateTime) {
        encoder.encodeString(obj.toString())
    }
}
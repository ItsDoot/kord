package pw.dotdash.kord.internal.serial

import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor

@Serializer(StringBuilder::class)
object StringBuilderSerializer : KSerializer<StringBuilder> {

    override val descriptor: SerialDescriptor = StringDescriptor.withName("StringBuilder")

    override fun deserialize(decoder: Decoder): StringBuilder {
        return StringBuilder(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, obj: StringBuilder) {
        encoder.encodeString(obj.toString())
    }
}
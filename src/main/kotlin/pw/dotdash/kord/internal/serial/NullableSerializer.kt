package pw.dotdash.kord.internal.serial

import kotlinx.serialization.Decoder
import kotlinx.serialization.Encoder
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialDescriptor

val <T : Any> KSerializer<T>.nullable: KSerializer<T?>
    get() = NullableSerializer(this)

class NullableSerializer<T : Any>(val serializer: KSerializer<T>) : KSerializer<T?> {

    override val descriptor: SerialDescriptor = serializer.descriptor

    override fun deserialize(decoder: Decoder): T? =
        if (decoder.decodeNotNullMark()) {
            decoder.decodeSerializableValue(serializer)
        } else {
            decoder.decodeNull()
        }

    override fun serialize(encoder: Encoder, obj: T?) {
        encoder.encodeNullableSerializableValue(serializer, obj)
    }
}
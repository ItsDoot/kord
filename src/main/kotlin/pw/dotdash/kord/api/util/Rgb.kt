package pw.dotdash.kord.api.util

import kotlinx.serialization.*
import kotlinx.serialization.internal.IntDescriptor

@Serializable(Rgb.Companion::class)
data class Rgb(val red: Int, val green: Int, val blue: Int) {
    constructor(value: Int) : this(value shr 16 and 255, value shr 8 and 255, value and 255)

    fun toInt(): Int = ((red and 255 shl 16) or (green and 255 shl 8) or (blue and 255))

    @Serializer(Rgb::class)
    companion object : KSerializer<Rgb> {
        val ZERO = Rgb(0)

        override val descriptor: SerialDescriptor = IntDescriptor.withName("Rgb")

        override fun deserialize(decoder: Decoder): Rgb {
            return Rgb(decoder.decodeInt())
        }

        override fun serialize(encoder: Encoder, obj: Rgb) {
            encoder.encodeInt(obj.toInt())
        }
    }
}
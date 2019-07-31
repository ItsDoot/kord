package pw.dotdash.kord.internal.serial

import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor

inline fun <reified T> indexedEnumSerializer(default: T? = null): KSerializer<T> where T : Enum<T>, T : IndexedValue =
    object : KSerializer<T> {
        private val values = enumValues<T>()
        override val descriptor: SerialDescriptor = StringDescriptor.withName(T::class.java.simpleName)

        override fun deserialize(decoder: Decoder): T {
            val value = decoder.decodeInt()
            return values.find { it.value == value }
                ?: default
                ?: throw SerializationException("No value found for index $value")
        }

        override fun serialize(encoder: Encoder, obj: T) {
            encoder.encodeInt(obj.value)
        }
    }

inline fun <reified T : Enum<T>> ordinalEnumSerializer(default: T? = null): KSerializer<T> =
    object : KSerializer<T> {
        private val values = enumValues<T>()
        override val descriptor: SerialDescriptor = StringDescriptor.withName(T::class.java.simpleName)

        override fun deserialize(decoder: Decoder): T {
            val value = decoder.decodeInt()
            return values.getOrNull(value)
                ?: default
                ?: throw SerializationException("No value found for ordinal $value")
        }

        override fun serialize(encoder: Encoder, obj: T) {
            encoder.encodeInt(obj.ordinal)
        }
    }
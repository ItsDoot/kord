package pw.dotdash.kord.api.util

import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

@Serializable(Option.Serialization::class)
sealed class Option<out T> {

    companion object {
        operator fun <T> invoke(value: T?): Option<T> = when (value) {
            null -> None
            else -> Some(value)
        }
    }

    val isPresent: Boolean get() = this is Some

    val isEmpty: Boolean get() = this == None

    fun get(): T =
        when (this) {
            is Some -> this.value
            else -> throw NoSuchElementException("Option is empty")
        }

    inline fun ifPresent(block: (T) -> Unit) {
        if (this is Some) {
            block(this.value)
        }
    }

    inline fun filter(predicate: (T) -> Boolean): Option<T> =
        when (this) {
            is Some -> if (predicate(this.value)) this else None
            else -> None
        }

    inline fun <R> map(transform: (T) -> R): Option<R> =
        when (this) {
            is Some -> Some(transform(this.value))
            else -> None
        }

    inline fun <R> flatMap(transform: (T) -> Option<R>): Option<R> =
        when (this) {
            is Some -> transform(this.value)
            else -> None
        }

    fun asSequence(): Sequence<T> = when (this) {
        is Some -> sequenceOf(this.value)
        else -> emptySequence()
    }

    @Serializer(Option::class)
    class Serialization<T>(private val element: KSerializer<T>) : KSerializer<Option<T>> {
        override val descriptor: SerialDescriptor get() = element.descriptor

        override fun deserialize(decoder: Decoder): Option<T> =
            Some(decoder.decodeSerializableValue(element))

        override fun serialize(encoder: Encoder, obj: Option<T>) {
            if (obj is Some) {
                encoder.encodeSerializableValue(element, obj.value)
            }
        }
    }
}

fun <T> Option<T>.orElse(other: T): T =
    when (this) {
        is Some -> this.value
        else -> other
    }

inline fun <T> Option<T>.orGet(other: () -> T): T =
    when (this) {
        is Some -> this.value
        else -> other()
    }

inline fun <T> Option<T>.orThrow(supply: () -> Throwable): T =
    when (this) {
        is Some -> this.value
        else -> throw supply()
    }

fun <T> Option<T>.or(other: Option<T>): Option<T> =
    when (this) {
        is Some -> this
        else -> other
    }

data class Some<out T>(val value: T) : Option<T>() {
    override fun equals(other: Any?): Boolean = this === other || (other is Some<*> && this.value == other.value)
    override fun hashCode(): Int = value.hashCode()
    override fun toString(): String = "Some($value)"
}

object None : Option<Nothing>() {
    override fun equals(other: Any?): Boolean = this === other
    override fun hashCode(): Int = 0
    override fun toString(): String = "None"
}

val <T> KSerializer<T>.option: KSerializer<Option<T>>
    get() = Option.serializer(this)

fun main() {
    val json = Json(JsonConfiguration(encodeDefaults = false))

    val bob = Person("bob", Some(75035))
    val john = Person("john")

    println(json.stringify(Person.serializer(), bob))
    println(json.stringify(Person.serializer(), john))

    println(json.parse(Person.serializer(), """{"name":"bob","zipcode":75035}"""))
    println(json.parse(Person.serializer(), """{"name":"john"}"""))
}

@Serializable
data class Person(val name: String, val zipcode: Option<Int> = None)
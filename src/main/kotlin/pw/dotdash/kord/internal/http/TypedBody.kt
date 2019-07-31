package pw.dotdash.kord.internal.http

import kotlinx.serialization.KSerializer

data class TypedBody<B>(val serializer: KSerializer<in B>, val body: B)
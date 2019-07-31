package pw.dotdash.kord.internal.http

import io.ktor.client.response.HttpResponse

data class TypedResponse<out R>(val response: HttpResponse, val value: R?)
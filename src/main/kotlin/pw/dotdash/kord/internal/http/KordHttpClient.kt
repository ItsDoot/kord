package pw.dotdash.kord.internal.http

import io.ktor.client.HttpClient
import io.ktor.client.call.call
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.client.request.url
import io.ktor.client.response.HttpResponse
import io.ktor.client.response.readText
import io.ktor.http.*
import io.ktor.http.content.TextContent
import kotlinx.serialization.KSerializer
import kotlinx.serialization.list
import pw.dotdash.kord.api.exception.InvalidRequestException
import pw.dotdash.kord.api.exception.InvalidTokenException
import pw.dotdash.kord.internal.KordImpl
import pw.dotdash.kord.internal.KordImpl.Companion.JSON
import pw.dotdash.kord.internal.entity.LazyEntity
import pw.dotdash.kord.internal.entity.guild.LazyGuildEntity

class KordHttpClient(val kord: KordImpl, val token: String, val http: HttpClient) {

    companion object {
        const val apiVersion = 6
        const val apiUrl = "https://discordapp.com/api/v$apiVersion"
        const val cdnUrl = "https://cdn.discordapp.com"

        const val userAgent = "DiscordBot (https://github.com/thedoot/kord, 0.1.0)"
    }

    suspend inline fun <B> request(
        method: HttpMethod,
        path: String,
        body: TypedBody<B>? = null,
        crossinline extra: HttpRequestBuilder.() -> Unit = {}
    ): HttpResponse {
        val response = http.call {
            this.method = method
            this.url("$apiUrl/$path")
            header(HttpHeaders.Authorization, "Bot $token")
            header(HttpHeaders.UserAgent, userAgent)
            accept(ContentType.Application.Json)
            if (body != null) {
                this.body = TextContent(
                    JSON.stringify(body.serializer, body.body),
                    contentType() ?: ContentType.Application.Json
                )
            }
            extra()
        }.response

        when (response.status.value) {
            400 -> throw InvalidRequestException()
            401 -> throw InvalidTokenException()
        }

        return response
    }

    suspend inline fun <B, R> request(
        method: HttpMethod,
        path: String,
        serializer: KSerializer<out R>,
        body: TypedBody<B>? = null,
        crossinline extra: HttpRequestBuilder.() -> Unit = {}
    ): TypedResponse<R> =
        request(method, path, body, extra).let { TypedResponse(it, JSON.parse(serializer, it.readText())) }

    //////////////////////////////////////////////////
    // Get Methods
    //////////////////////////////////////////////////

    suspend inline fun <R> get(
        path: String,
        serializer: KSerializer<out R>,
        crossinline extra: HttpRequestBuilder.() -> Unit = {}
    ): R? = request<Nothing, R>(HttpMethod.Get, path, serializer, null, extra).value

    suspend inline fun <R> getList(
        path: String,
        serializer: KSerializer<out R>,
        crossinline extra: HttpRequestBuilder.() -> Unit = {}
    ): List<R> = get(path, serializer.list, extra).orEmpty()

    suspend inline fun <R : LazyEntity> getEntity(
        path: String,
        serializer: KSerializer<out R>,
        crossinline extra: HttpRequestBuilder.() -> Unit = {}
    ): R? = get(path, serializer, extra)?.also { it.init(kord) }

    suspend inline fun <R : LazyGuildEntity> getGuildEntity(
        path: String,
        serializer: KSerializer<out R>,
        guildId: Long,
        crossinline extra: HttpRequestBuilder.() -> Unit = {}
    ): R? = get(path, serializer, extra)?.also {
        it.init(kord)
        it.guildId = guildId
    }

    suspend inline fun <R : LazyEntity> getEntityList(
        path: String,
        serializer: KSerializer<out R>,
        crossinline extra: HttpRequestBuilder.() -> Unit = {}
    ): List<R> = getList(path, serializer, extra).also { for (value in it) value.init(kord) }

    suspend inline fun <R : LazyGuildEntity> getGuildEntityList(
        path: String,
        serializer: KSerializer<out R>,
        guildId: Long,
        crossinline extra: HttpRequestBuilder.() -> Unit = {}
    ): List<R> = getList(path, serializer, extra).also {
        for (value in it) {
            value.init(kord)
            value.guildId = guildId
        }
    }

    //////////////////////////////////////////////////
    // Post Methods
    //////////////////////////////////////////////////

    suspend inline fun <B, R> post(
        path: String,
        serializer: KSerializer<out R>,
        body: TypedBody<B>? = null,
        crossinline extra: HttpRequestBuilder.() -> Unit = {}
    ): R? = request(HttpMethod.Post, path, serializer, body, extra).value

    suspend inline fun <B> post(
        path: String,
        body: TypedBody<B>? = null,
        crossinline extra: HttpRequestBuilder.() -> Unit = {}
    ): Boolean = request(HttpMethod.Post, path, body, extra).status.isSuccess()

    suspend inline fun post(
        path: String,
        crossinline extra: HttpRequestBuilder.() -> Unit = {}
    ): Boolean = post<Nothing>(path, null, extra)

    suspend inline fun <B, R : LazyEntity> postEntity(
        path: String,
        serializer: KSerializer<out R>,
        body: TypedBody<B>? = null,
        crossinline extra: HttpRequestBuilder.() -> Unit = {}
    ): R? = post(path, serializer, body, extra)?.also { it.init(kord) }

    suspend inline fun <B, R : LazyGuildEntity> postGuildEntity(
        path: String,
        serializer: KSerializer<out R>,
        body: TypedBody<B>? = null,
        guildId: Long,
        crossinline extra: HttpRequestBuilder.() -> Unit = {}
    ): R? = post(path, serializer, body, extra)?.also {
        it.init(kord)
        it.guildId = guildId
    }

    //////////////////////////////////////////////////
    // Put Methods
    //////////////////////////////////////////////////

    suspend inline fun <B, R> put(
        path: String,
        serializer: KSerializer<out R>,
        body: TypedBody<B>? = null,
        crossinline extra: HttpRequestBuilder.() -> Unit = {}
    ): R? = request(HttpMethod.Put, path, serializer, body, extra).value

    suspend inline fun <B, R : LazyEntity> putEntity(
        path: String,
        serializer: KSerializer<out R>,
        body: TypedBody<B>? = null,
        crossinline extra: HttpRequestBuilder.() -> Unit = {}
    ): R? = put(path, serializer, body, extra)?.also {
        it.init(kord)
    }

    suspend inline fun <B, R : LazyGuildEntity> putGuildEntity(
        path: String,
        serializer: KSerializer<out R>,
        body: TypedBody<B>? = null,
        guildId: Long,
        crossinline extra: HttpRequestBuilder.() -> Unit = {}
    ): R? = put(path, serializer, body, extra)?.also {
        it.init(kord)
        it.guildId = guildId
    }

    suspend inline fun <B> put(
        path: String,
        body: TypedBody<B>? = null,
        crossinline extra: HttpRequestBuilder.() -> Unit = {}
    ): Boolean = request(HttpMethod.Put, path, body, extra).status.isSuccess()

    suspend inline fun put(
        path: String,
        crossinline extra: HttpRequestBuilder.() -> Unit = {}
    ): Boolean = put<Nothing>(path, null, extra)

    //////////////////////////////////////////////////
    // Patch Methods
    //////////////////////////////////////////////////

    suspend inline fun <B, R> patch(
        path: String,
        serializer: KSerializer<out R>,
        body: TypedBody<B>? = null,
        crossinline extra: HttpRequestBuilder.() -> Unit = {}
    ): R? = request(HttpMethod.Patch, path, serializer, body, extra).value

    suspend inline fun <B, R : LazyEntity> patchEntity(
        path: String,
        serializer: KSerializer<out R>,
        body: TypedBody<B>? = null,
        crossinline extra: HttpRequestBuilder.() -> Unit = {}
    ): R? = patch(path, serializer, body, extra)?.also {
        it.init(kord)
    }

    suspend inline fun <B, R : LazyGuildEntity> patchGuildEntity(
        path: String,
        serializer: KSerializer<out R>,
        body: TypedBody<B>? = null,
        guildId: Long,
        crossinline extra: HttpRequestBuilder.() -> Unit = {}
    ): R? = patch(path, serializer, body, extra)?.also {
        it.init(kord)
        it.guildId = guildId
    }

    suspend inline fun <B> patch(
        path: String,
        body: TypedBody<B>? = null,
        crossinline extra: HttpRequestBuilder.() -> Unit = {}
    ): Boolean = request(HttpMethod.Patch, path, body, extra).status.isSuccess()

    suspend inline fun patch(
        path: String,
        crossinline extra: HttpRequestBuilder.() -> Unit = {}
    ): Boolean = patch<Nothing>(path, null, extra)

    //////////////////////////////////////////////////
    // Delete Methods
    //////////////////////////////////////////////////

    suspend inline fun <B> delete(
        path: String,
        body: TypedBody<B>? = null,
        crossinline extra: HttpRequestBuilder.() -> Unit = {}
    ): Boolean = request(HttpMethod.Delete, path, body, extra).status.isSuccess()

    suspend inline fun delete(
        path: String,
        crossinline extra: HttpRequestBuilder.() -> Unit = {}
    ): Boolean = delete<Nothing>(path, null, extra)
}
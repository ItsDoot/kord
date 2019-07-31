package pw.dotdash.kord.api.cache

import pw.dotdash.kord.api.entity.Identifiable

interface MutableSnowflakeCache<V> : SnowflakeCache<V> {

    suspend fun set(id: Long, value: V)

    suspend infix fun remove(id: Long): Boolean

    suspend fun clear()
}

suspend inline fun <V : Identifiable> MutableSnowflakeCache<V>.getOrFetch(id: Long, supplier: suspend () -> V?): V? =
    this.get(id) ?: supplier()?.also { this.set(it.id, it) }
package pw.dotdash.kord.api.cache.simple

import pw.dotdash.kord.api.cache.MutableSnowflakeCache

infix fun <V> MutableSnowflakeCache<V>.compose(second: MutableSnowflakeCache<V>): ComposedMutableSnowflakeCache<V> =
    ComposedMutableSnowflakeCache(this, second)

data class ComposedMutableSnowflakeCache<V>(
    val first: MutableSnowflakeCache<V>,
    val second: MutableSnowflakeCache<V>
) : MutableSnowflakeCache<V> {

    override suspend fun get(id: Long): V? = first get id ?: second get id

    override suspend fun has(id: Long): Boolean = first has id || second has id

    override suspend fun set(id: Long, value: V) {
        first.set(id, value)
        second.set(id, value)
    }

    override suspend fun remove(id: Long): Boolean = first.remove(id) or second.remove(id)

    override suspend fun clear() {
        first.clear()
        second.clear()
    }
}
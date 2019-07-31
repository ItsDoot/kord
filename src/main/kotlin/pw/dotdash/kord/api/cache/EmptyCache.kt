package pw.dotdash.kord.api.cache

fun <V> emptyCache(): MutableSnowflakeCache<V> =
    @Suppress("UNCHECKED_CAST") (EmptyCache as MutableSnowflakeCache<V>)

private object EmptyCache : MutableSnowflakeCache<Any?> {
    override suspend fun get(id: Long): Any? = null
    override suspend fun has(id: Long): Boolean = false
    override suspend fun set(id: Long, value: Any?) = Unit
    override suspend fun remove(id: Long): Boolean = false
    override suspend fun clear() = Unit
}
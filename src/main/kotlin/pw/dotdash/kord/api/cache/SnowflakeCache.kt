package pw.dotdash.kord.api.cache

interface SnowflakeCache<out V> {

    suspend infix fun get(id: Long): V?

    suspend infix fun has(id: Long): Boolean
}
package pw.dotdash.kord.api.cache.simple

import pw.dotdash.kord.api.cache.MutableSnowflakeCache
import java.util.concurrent.ConcurrentHashMap

class ConcurrentHashMapCache<V>: MutableSnowflakeCache<V> {
    private val map: MutableMap<Long, V>

    constructor(map: Map<Long, V>) {
        this.map = ConcurrentHashMap(map)
    }

    constructor() {
        this.map = ConcurrentHashMap()
    }

    override suspend fun get(id: Long): V? {
        return map[id]
    }

    override suspend fun has(id: Long): Boolean {
        return id in map
    }

    override suspend fun set(id: Long, value: V) {
        map[id] = value
    }

    override suspend fun remove(id: Long): Boolean {
        return map.remove(id) != null
    }

    override suspend fun clear() {
        map.clear()
    }
}
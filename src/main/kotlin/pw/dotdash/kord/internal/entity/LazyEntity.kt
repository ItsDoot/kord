package pw.dotdash.kord.internal.entity

import pw.dotdash.kord.internal.KordImpl
import pw.dotdash.kord.api.entity.Entity

interface LazyEntity : Entity {

    override var kord: KordImpl

    suspend fun init(kord: KordImpl)
}
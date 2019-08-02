package pw.dotdash.kord.internal.event

import pw.dotdash.kord.api.event.Event
import pw.dotdash.kord.internal.KordImpl

interface LazyEvent : Event {

    suspend fun init(kord: KordImpl)

    suspend fun preProcess() = Unit

    suspend fun postProcess() = Unit
}
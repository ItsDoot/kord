package pw.dotdash.kord.internal.event

import pw.dotdash.kord.api.event.Event
import pw.dotdash.kord.internal.KordImpl

interface LazyEvent : Event {

    fun init(kord: KordImpl)
}
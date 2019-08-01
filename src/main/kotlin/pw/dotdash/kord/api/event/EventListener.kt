package pw.dotdash.kord.api.event

import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import pw.dotdash.kord.api.event.guild.CreateGuildEvent
import pw.dotdash.kord.api.event.message.CreateMessageEvent
import pw.dotdash.kord.api.event.message.DeleteMessageEvent

suspend fun ReceiveChannel<Event>.listen(listener: EventListener) {
    for (event in this) {
        when (event) {
            is ReadyEvent -> coroutineScope { launch { listener.onReady(event) } }
            is CreateGuildEvent -> coroutineScope { launch { listener.onCreateGuild(event) } }
            is CreateMessageEvent -> coroutineScope { launch { listener.onCreateMessage(event) } }
            is DeleteMessageEvent -> coroutineScope { launch { listener.onDeleteMessage(event) } }
        }
    }
}

interface EventListener {

    suspend fun onReady(event: ReadyEvent) = Unit

    suspend fun onCreateGuild(event: CreateGuildEvent) = Unit

    suspend fun onCreateMessage(event: CreateMessageEvent) = Unit

    suspend fun onDeleteMessage(event: DeleteMessageEvent) = Unit
}
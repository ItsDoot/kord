package pw.dotdash.kord.api.util

import kotlinx.coroutines.channels.ReceiveChannel

suspend inline fun <reified R> ReceiveChannel<*>.waitFor(): R {
    for (event in this) {
        if (event is R) {
            return event
        }
    }

    throw IllegalStateException("Did not receive a ${R::class}")
}

suspend inline fun <reified R> ReceiveChannel<*>.waitFor(predicate: (R) -> Boolean): R {
    for (event in this) {
        if (event is R && predicate(event)) {
            return event
        }
    }

    throw IllegalStateException("Did not receive a ${R::class}")
}
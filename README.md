# Kord

A discord api wrapper for Kotlin, using coroutines.

## Gradle

```kotlin
repositories {
    maven { setUrl("https://jitpack.io") }
}

dependencies {
    implementation("com.github.itsdoot:kord:<LATEST>")
}
```

## Example

```kotlin
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import pw.dotdash.kord.api.Kord
import pw.dotdash.kord.api.event.EventListener
import pw.dotdash.kord.api.event.ReadyEvent
import pw.dotdash.kord.api.event.guild.CreateGuildEvent
import pw.dotdash.kord.api.event.listen
import pw.dotdash.kord.api.event.message.CreateMessageEvent
import pw.dotdash.kord.api.event.message.DeleteMessageEvent

val logger = KotlinLogging.logger("mybot")

fun main(args: Array<String>) = runBlocking<Unit> {
    val kord = Kord(args[0])
    
    launch {
        kord.events.openSubscription().listen(Listener())
    }
    
    kord.connect()
}

object Listener : EventListener {

    override suspend fun onReady(event: ReadyEvent) {
        logger.info { "Logged in as ${event.user.username}#${event.user.discriminator}" }
    }

    override suspend fun onCreateGuild(event: CreateGuildEvent) {
        logger.info { "Found ${event.guild.name}" }
    }

    override suspend fun onCreateMessage(event: CreateMessageEvent) {
        if (event.message.content == "creeper") {
            event.message.channel().createMessage {
                content {
                    append("aw man")
                }
            }
        }
    }

    override suspend fun onDeleteMessage(event: DeleteMessageEvent) {
        logger.info { "oopsie woopsie we deweted a messig owo" }
    }
}
```
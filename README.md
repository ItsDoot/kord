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
import pw.dotdash.kord.api.Kord
import pw.dotdash.kord.api.event.ReadyEvent
import pw.dotdash.kord.api.event.message.CreateMessageEvent
import pw.dotdash.kord.api.util.waitFor

fun main(args: Array<String>) = runBlocking<Unit> {
    val kord = Kord(args[0])
    
    launch {
        val events = kord.events.openSubscription()
    
        val ready = events.waitFor<ReadyEvent>()
        println("Logged in as ${ready.user.username}#${ready.user.discriminator}")
    
        for (event in events) when (event) {
            is CreateMessageEvent -> {
                if (event.message.content == "creeper") {
                    event.message.channel().createMessage {
                        content {
                            append("aw man")
                        }
                    }
                }
            }
        }
    }
    
    kord.connect()
}
```
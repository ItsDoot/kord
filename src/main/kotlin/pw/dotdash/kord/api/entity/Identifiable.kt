package pw.dotdash.kord.api.entity

import java.time.Instant

interface Identifiable {

    val id: Long

    val createdAt: Instant
        get() = Instant.ofEpochMilli((id ushr 22) + 1420070400000L)
}
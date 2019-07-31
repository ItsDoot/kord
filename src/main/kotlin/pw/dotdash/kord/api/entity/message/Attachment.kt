package pw.dotdash.kord.api.entity.message

import pw.dotdash.kord.api.entity.Identifiable

interface Attachment : Identifiable {

    override val id: Long

    val filename: String

    val size: Int

    val url: String

    val proxyUrl: String

    val height: Int?

    val width: Int?
}
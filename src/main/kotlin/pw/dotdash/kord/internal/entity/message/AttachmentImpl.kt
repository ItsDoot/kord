package pw.dotdash.kord.internal.entity.message

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import pw.dotdash.kord.api.entity.message.Attachment

@Serializable
data class AttachmentImpl(
    override val id: Long,
    override val filename: String,
    override val size: Int,
    override val url: String,
    @SerialName("proxy_url") override val proxyUrl: String,
    override val height: Int?,
    override val width: Int?
) : Attachment
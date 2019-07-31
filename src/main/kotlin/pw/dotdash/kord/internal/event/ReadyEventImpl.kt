package pw.dotdash.kord.internal.event

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import pw.dotdash.kord.api.event.ReadyEvent
import pw.dotdash.kord.api.util.ShardInfo
import pw.dotdash.kord.internal.KordImpl
import pw.dotdash.kord.internal.entity.guild.UnavailableGuildImpl
import pw.dotdash.kord.internal.entity.user.SelfUserImpl

@Serializable
data class ReadyEventImpl(
    @SerialName("v") override val version: Int,
    override val user: SelfUserImpl,
    override val guilds: List<UnavailableGuildImpl>,
    @SerialName("session_id") override val sessionId: String,
    override val shard: ShardInfo? = null
) : ReadyEvent, LazyEvent {
    @Transient
    override lateinit var kord: KordImpl

    override fun init(kord: KordImpl) {
        this.kord = kord
    }
}
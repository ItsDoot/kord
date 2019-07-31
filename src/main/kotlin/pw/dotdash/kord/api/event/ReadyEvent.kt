package pw.dotdash.kord.api.event

import pw.dotdash.kord.api.Kord
import pw.dotdash.kord.api.entity.guild.UnavailableGuild
import pw.dotdash.kord.api.entity.user.SelfUser
import pw.dotdash.kord.api.util.ShardInfo

interface ReadyEvent : Event {

    override val kord: Kord

    /**
     * The gateway protocol version.
     */
    val version: Int

    /**
     * The current user.
     */
    val user: SelfUser

    /**
     * The guilds that the current user is in.
     */
    val guilds: List<UnavailableGuild>

    /**
     * The session id used for resuming connections.
     */
    val sessionId: String

    /**
     * The shard information associated with this session.
     */
    val shard: ShardInfo?
}
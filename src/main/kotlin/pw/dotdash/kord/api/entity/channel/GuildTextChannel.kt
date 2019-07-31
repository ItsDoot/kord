package pw.dotdash.kord.api.entity.channel

import pw.dotdash.kord.api.entity.Mentionable

interface GuildTextChannel : GuildChannel, TextChannel, Mentionable {

    val topic: String?

    val rateLimitPerUser: Int

    suspend fun bulkDelete(messages: List<Long>): Boolean

    suspend fun bulkDelete(vararg messages: Long): Boolean = bulkDelete(messages.toList())

    override fun asMention(): String = "<#$id>"
}
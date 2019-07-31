package pw.dotdash.kord.api.entity.guild

interface SelfMember : Member {

    suspend fun modifyNick(nick: String): Boolean
}
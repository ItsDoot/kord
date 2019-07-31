package pw.dotdash.kord.api.entity

import java.lang.StringBuilder

interface Mentionable {

    fun asMention(): String
}

fun StringBuilder.appendMentionable(mentionable: Mentionable) : StringBuilder = append(mentionable.asMention())
package pw.dotdash.kord.api.entity.guild

enum class Permission(val offset: Int, val isChannel: Boolean, val isGuild: Boolean) {
    CREATE_INSTANT_INVITE(0, true, true),
    KICK_MEMBERS(1, false, true),
    BAN_MEMBERS(2, false, true),
    ADMINISTRATOR(3, false, true),
    MANAGE_CHANNELS(4, true, true),
    MANAGE_GUILD(5, false, true),
    ADD_REACTIONS(6, true, true),
    VIEW_AUDIT_LOG(7, false, true),
    PRIORITY_SPEAKER(8, true, true),
    VIEW_CHANNEL(10, true, true),
    SEND_MESSAGES(11, true, true),
    SEND_TTS_MESSAGES(12, true, true),
    MANAGE_MESSAGES(13, true, true),
    EMBED_LINKS(14, true, true),
    ATTACH_FILES(15, true, true),
    READ_MESSAGE_HISTORY(16, true, true),
    MENTION_EVERYONE(17, true, true),
    USE_EXTERNAL_EMOJIS(18, true, true),
    CONNECT(20, true, true),
    SPEAK(21, true, true),
    MUTE_MEMBERS(22, true, true),
    DEAFEN_MEMBERS(23, true, true),
    MOVE_MEMBERS(24, true, true),
    USE_VAD(25, true, true),
    CHANGE_NICKNAME(26, false, true),
    MANAGE_NICKNAMES(27, false, true),
    MANAGE_ROLES(28, true, true),
    MANAGE_WEBHOOKS(29, true, true),
    MANAGE_EMOJIS(30, false, true);

    val raw: Long = 1L shl offset

    val text: Boolean get() = raw and ALL_TEXT == raw

    val voice: Boolean get() = raw and ALL_VOICE == raw

    companion object {

        val ALL: Long = PermissionSet.raw(*values())

        val ALL_CHANNEL: Long = PermissionSet.raw(values().filter(Permission::isChannel))

        val ALL_GUILD: Long = PermissionSet.raw(values().filter(Permission::isGuild))

        val ALL_TEXT: Long = PermissionSet.raw(
            ADD_REACTIONS, SEND_MESSAGES, SEND_TTS_MESSAGES, MANAGE_MESSAGES,
            EMBED_LINKS, ATTACH_FILES, READ_MESSAGE_HISTORY, MENTION_EVERYONE
        )

        val ALL_VOICE: Long = PermissionSet.raw(CONNECT, SPEAK, MUTE_MEMBERS, DEAFEN_MEMBERS, MOVE_MEMBERS, USE_VAD)

        fun fromOffset(offset: Int): Permission? = values().find { it.offset == offset }
    }
}
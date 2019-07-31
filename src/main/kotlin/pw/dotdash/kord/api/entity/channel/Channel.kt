package pw.dotdash.kord.api.entity.channel

import pw.dotdash.kord.api.entity.Entity
import pw.dotdash.kord.api.entity.Identifiable

interface Channel : Entity, Identifiable {

    val type: ChannelType
}
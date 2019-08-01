package pw.dotdash.kord.internal.event.guild

import kotlinx.serialization.*
import pw.dotdash.kord.api.event.guild.CreateGuildEvent
import pw.dotdash.kord.internal.KordImpl
import pw.dotdash.kord.internal.entity.guild.GuildImpl
import pw.dotdash.kord.internal.event.LazyEvent

@Serializable(CreateGuildEventImpl.Companion::class)
data class CreateGuildEventImpl(override val guild: GuildImpl) : CreateGuildEvent, LazyEvent {
    @Transient
    override lateinit var kord: KordImpl

    override fun init(kord: KordImpl) {
        this.kord = kord
    }

    @Serializer(CreateGuildEventImpl::class)
    companion object : KSerializer<CreateGuildEventImpl> {
        private val serializer = GuildImpl.serializer()

        override val descriptor: SerialDescriptor = serializer.descriptor

        override fun deserialize(decoder: Decoder): CreateGuildEventImpl {
            return CreateGuildEventImpl(serializer.deserialize(decoder))
        }

        override fun serialize(encoder: Encoder, obj: CreateGuildEventImpl) {
            serializer.serialize(encoder, obj.guild)
        }
    }
}
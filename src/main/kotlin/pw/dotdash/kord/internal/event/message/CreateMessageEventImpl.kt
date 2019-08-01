package pw.dotdash.kord.internal.event.message

import kotlinx.serialization.*
import pw.dotdash.kord.api.event.message.CreateMessageEvent
import pw.dotdash.kord.internal.KordImpl
import pw.dotdash.kord.internal.entity.message.MessageImpl
import pw.dotdash.kord.internal.event.LazyEvent

@Serializable(CreateMessageEventImpl.Companion::class)
data class CreateMessageEventImpl(override val message: MessageImpl) : CreateMessageEvent, LazyEvent {
    @Transient
    override lateinit var kord: KordImpl

    @Transient
    override val guildId: Long? get() = message.guildId

    @Transient
    override val channelId: Long get() = message.channelId

    @Transient
    override val messageId: Long get() = message.id

    override fun init(kord: KordImpl) {
        this.kord = kord
        message.kord = kord
    }

    @Serializer(CreateMessageEventImpl::class)
    companion object : KSerializer<CreateMessageEventImpl> {
        private val serializer = MessageImpl.serializer()

        override val descriptor: SerialDescriptor = serializer.descriptor

        override fun deserialize(decoder: Decoder): CreateMessageEventImpl {
            return CreateMessageEventImpl(serializer.deserialize(decoder))
        }

        override fun serialize(encoder: Encoder, obj: CreateMessageEventImpl) {
            serializer.serialize(encoder, obj.message)
        }
    }
}
package pw.dotdash.kord.api.util

import kotlinx.serialization.*

@Serializable
data class ShardInfo(val shardId: Int, val numShards: Int) {

    companion object : KSerializer<ShardInfo> {
        private val serializer = Int.serializer().list

        override val descriptor: SerialDescriptor = serializer.descriptor

        override fun deserialize(decoder: Decoder): ShardInfo {
            val (shardId, numShards) = serializer.deserialize(decoder)
            return ShardInfo(shardId, numShards)
        }

        override fun serialize(encoder: Encoder, obj: ShardInfo) {
            serializer.serialize(encoder, listOf(obj.shardId, obj.numShards))
        }
    }
}
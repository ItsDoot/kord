package pw.dotdash.kord.internal.entity.message

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import pw.dotdash.kord.api.entity.message.Embed
import pw.dotdash.kord.api.util.Rgb
import pw.dotdash.kord.internal.serial.OffsetDateTimeSerializer
import java.time.OffsetDateTime

@Serializable
data class EmbedImpl(
    override val title: String? = null,
    override val type: String? = null,
    override val description: String? = null,
    override val url: String? = null,
    @Serializable(OffsetDateTimeSerializer::class) override val timestamp: OffsetDateTime? = null,
    override val color: Rgb? = null,
    override val footer: FooterImpl? = null,
    override val image: ImageImpl? = null,
    override val thumbnail: ThumbnailImpl? = null,
    override val video: VideoImpl? = null,
    override val provider: ProviderImpl? = null,
    override val author: AuthorImpl? = null,
    override val fields: List<FieldImpl> = emptyList()
) : Embed {

    @Serializable
    data class FooterImpl(
        override val text: String,
        @SerialName("icon_url") override val iconUrl: String? = null,
        @SerialName("proxy_icon_url") override val proxyIconUrl: String? = null
    ) : Embed.Footer {

        @Serializable
        data class BuilderImpl(
            override var text: String = "",
            @SerialName("icon_url") override var iconUrl: String? = null,
            @SerialName("proxy_icon_url") override var proxyIconUrl: String? = null
        ) : Embed.Footer.Builder
    }

    @Serializable
    data class ImageImpl(
        override val url: String? = null,
        @SerialName("proxy_url") override val proxyUrl: String? = null,
        override val height: Int? = null,
        override val width: Int? = null
    ) : Embed.Image {

        @Serializable
        data class BuilderImpl(
            override var url: String? = null,
            @SerialName("proxy_url") override var proxyUrl: String? = null,
            override var height: Int? = null,
            override var width: Int? = null
        ) : Embed.Image.Builder
    }

    @Serializable
    data class ThumbnailImpl(
        override val url: String? = null,
        @SerialName("proxy_url") override val proxyUrl: String? = null,
        override val height: Int? = null,
        override val width: Int? = null
    ) : Embed.Thumbnail {

        @Serializable
        data class BuilderImpl(
            override var url: String? = null,
            @SerialName("proxy_url") override var proxyUrl: String? = null,
            override var height: Int? = null,
            override var width: Int? = null
        ) : Embed.Thumbnail.Builder
    }

    @Serializable
    data class VideoImpl(
        override val url: String? = null,
        override val height: Int? = null,
        override val width: Int? = null
    ) : Embed.Video {

        @Serializable
        data class BuilderImpl(
            override var url: String? = null,
            override var height: Int? = null,
            override var width: Int? = null
        ) : Embed.Video.Builder
    }

    @Serializable
    data class ProviderImpl(
        override val name: String? = null,
        override val url: String? = null
    ) : Embed.Provider {

        @Serializable
        data class BuilderImpl(
            override var name: String? = null,
            override var url: String? = null
        ) : Embed.Provider.Builder
    }

    @Serializable
    data class AuthorImpl(
        override val name: String? = null,
        override val url: String? = null,
        @SerialName("icon_url") override val iconUrl: String? = null,
        @SerialName("proxy_icon_url") override val proxyIconUrl: String? = null
    ) : Embed.Author {

        @Serializable
        data class BuilderImpl(
            override var name: String? = null,
            override var url: String? = null,
            @SerialName("icon_url") override var iconUrl: String? = null,
            @SerialName("proxy_icon_url") override var iconProxyUrl: String? = null
        ) : Embed.Author.Builder
    }

    @Serializable
    data class FieldImpl(
        override val name: String,
        override val value: String,
        override val inline: Boolean? = null
    ) : Embed.Field {

        @Serializable
        data class BuilderImpl(
            override var name: String = "",
            override var value: String = "",
            override var inline: Boolean? = null
        ) : Embed.Field.Builder
    }

    @Serializable
    data class BuilderImpl(
        override var title: String? = null,
        override var type: String? = null,
        override var description: String? = null,
        override var url: String? = null,
        @Serializable(OffsetDateTimeSerializer::class) override var timestamp: OffsetDateTime? = null,
        override var color: Rgb? = null
    ) : Embed.Builder {

        var footer: FooterImpl.BuilderImpl? = null
        var image: ImageImpl.BuilderImpl? = null
        var thumbnail: ThumbnailImpl.BuilderImpl? = null
        var video: VideoImpl.BuilderImpl? = null
        var provider: ProviderImpl.BuilderImpl? = null
        var author: AuthorImpl.BuilderImpl? = null
        val fields = ArrayList<FieldImpl.BuilderImpl>()

        override fun footer(build: Embed.Footer.Builder.() -> Unit) {
            this.footer = FooterImpl.BuilderImpl().apply(build)
        }

        override fun image(build: Embed.Image.Builder.() -> Unit) {
            this.image = ImageImpl.BuilderImpl().apply(build)
        }

        override fun thumbnail(build: Embed.Thumbnail.Builder.() -> Unit) {
            this.thumbnail = ThumbnailImpl.BuilderImpl().apply(build)
        }

        override fun video(build: Embed.Video.Builder.() -> Unit) {
            this.video = VideoImpl.BuilderImpl().apply(build)
        }

        override fun provider(build: Embed.Provider.Builder.() -> Unit) {
            this.provider = ProviderImpl.BuilderImpl().apply(build)
        }

        override fun author(build: Embed.Author.Builder.() -> Unit) {
            this.author = AuthorImpl.BuilderImpl().apply(build)
        }

        override fun addField(build: Embed.Field.Builder.() -> Unit) {
            this.fields += FieldImpl.BuilderImpl().apply(build)
        }
    }
}
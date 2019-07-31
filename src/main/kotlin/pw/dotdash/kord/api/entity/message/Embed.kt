package pw.dotdash.kord.api.entity.message

import java.time.OffsetDateTime

interface Embed {

    val title: String?

    val type: String?

    val description: String?

    val url: String?

    val timestamp: OffsetDateTime?

    val color: Int?

    val footer: Footer?

    val image: Image?

    val thumbnail: Thumbnail?

    val video: Video?

    val provider: Provider?

    val author: Author?

    val fields: List<Field>

    interface Footer {

        val text: String

        val iconUrl: String?

        val proxyIconUrl: String?

        interface Builder {

            var text: String

            var iconUrl: String?

            var proxyIconUrl: String?
        }
    }

    interface Image {

        val url: String?

        val proxyUrl: String?

        val height: Int?

        val width: Int?

        interface Builder {

            var url: String?

            var proxyUrl: String?

            var height: Int?

            var width: Int?
        }
    }

    interface Thumbnail {

        val url: String?

        val proxyUrl: String?

        val height: Int?

        val width: Int?

        interface Builder {

            var url: String?

            var proxyUrl: String?

            var height: Int?

            var width: Int?
        }
    }

    interface Video {

        val url: String?

        val height: Int?

        val width: Int?

        interface Builder {

            var url: String?

            var height: Int?

            var width: Int?
        }
    }

    interface Provider {

        val name: String?

        val url: String?

        interface Builder {

            var name: String?

            var url: String?
        }
    }

    interface Author {

        val name: String?

        val url: String?

        val iconUrl: String?

        val proxyIconUrl: String?

        interface Builder {

            var name: String?

            var url: String?

            var iconUrl: String?

            var iconProxyUrl: String?
        }
    }

    interface Field {

        val name: String

        val value: String

        val inline: Boolean?

        interface Builder {

            var name: String

            var value: String

            var inline: Boolean?
        }
    }

    interface Builder {

        var title: String?

        var type: String?

        var description: String?

        var url: String?

        var timestamp: OffsetDateTime?

        var color: Int?

        fun footer(build: Footer.Builder.() -> Unit)

        fun image(build: Image.Builder.() -> Unit)

        fun thumbnail(build: Thumbnail.Builder.() -> Unit)

        fun video(build: Video.Builder.() -> Unit)

        fun provider(build: Provider.Builder.() -> Unit)

        fun author(build: Author.Builder.() -> Unit)

        fun addField(build: Field.Builder.() -> Unit)
    }
}
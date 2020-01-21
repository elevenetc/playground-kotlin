package renderer

fun main(args: Array<String>) {

    val config = StreamChat.Config()
    config.addMessageRenderer(MatchRenderer())
    StreamChat.init(config)
}

open class View {

}

class MatchView : View()

class Message(val type: String)

class MatchRenderer : MessageRenderer {

    override fun checkType(message: Message): Boolean {
        return "match" == message.type
    }

    override fun getView(message: Message): View {
        return MatchView()
    }

}

interface MessageRenderer {
    fun checkType(message: Message): Boolean
    fun getView(message: Message): View
}

class StreamChat {

    class Config {
        fun addMessageRenderer(renderer: MessageRenderer) {

        }
    }

    companion object {
        fun init(config: Config) {

        }
    }
}

class Channel(val id: String, val name: String, val updatedAt: Int)
class Resource<T>(val data: T, val updating: Boolean, val ready: Boolean)


class Cache {

    private val channels = mutableListOf<Channel>()

    fun upsert(channels: List<Channel>) {
        this.channels.addAll(channels)
    }

    fun get(): List<Channel> {
        return ArrayList(channels)
    }
}

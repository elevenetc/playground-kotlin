package network

open class Connection(val queue: MessageQueue = BlockingMessageQueue()) {

    @Volatile var isConnected: Boolean = false

    open fun onReady() {

    }

    open fun onNewMessage(message: String) {

    }

    fun sendMessage(message: String) {
        queue.add(message)
    }

    fun hasNext(): Boolean {
        return queue.size() > 0
    }

    fun nextMessageToSend(): String {
        return queue.get()
    }

    open fun onError(e: Exception) {

    }
}
package network

import java.util.concurrent.LinkedBlockingDeque

class BlockingMessageQueue : MessageQueue {

    val queue: LinkedBlockingDeque<String> = LinkedBlockingDeque()

    override fun get(): String {
        return queue.take()
    }

    override fun add(message: String) {
        queue.add(message)
    }

    override fun size(): Int {
        return queue.size
    }

}
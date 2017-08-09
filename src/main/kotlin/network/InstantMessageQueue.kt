package network

import java.util.*

class InstantMessageQueue : MessageQueue {

    val queue: Deque<String> = LinkedList()

    override fun get(): String {
        return queue.first
    }

    override fun add(message: String) {
        queue.add(message)
    }

    override fun size(): Int {
        return queue.size
    }

}
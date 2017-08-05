package utils

import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingDeque

open class Connection {

    val messageQueue = LinkedBlockingDeque<String>()

    open fun onConnected() {

    }

    open fun onNewMessage(message: String) {

    }

    fun sendMessage(message: String) {
        messageQueue.add(message)
    }

    fun getSendMessageQueue(): BlockingQueue<String> {
        return messageQueue
    }
}
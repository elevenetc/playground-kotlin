package utils

import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingDeque

open class ConnectionHandler {

    @Volatile var isConnected: Boolean = false
    val messageQueue = LinkedBlockingDeque<String>()

    open fun onReady() {

    }

    open fun onNewMessage(message: String) {

    }

    fun sendMessage(message: String) {
        messageQueue.add(message)
    }

    fun getSendMessageQueue(): BlockingQueue<String> {
        return messageQueue
    }

    @Synchronized
    fun onError(e: Exception) {

    }
}
package reactive.networks

import utils.ConnectionHandler

class OutThread(
        val socket: ISocket,
        val connectionHandler: ConnectionHandler) : Thread() {

    init {
        isDaemon = true
    }

    override fun run() {
        try {
            val queue = connectionHandler.getSendMessageQueue()
            while (connectionHandler.isConnected) {
                val msg = queue.take()
                socket.write(msg)
            }
        } catch (e: Exception) {
            connectionHandler.onError(e)
        }

    }
}
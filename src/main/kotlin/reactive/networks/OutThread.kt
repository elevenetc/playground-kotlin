package reactive.networks

import utils.Connection

class OutThread(
        val socket: ISocket,
        val connection: Connection) : Thread() {

    override fun run() {
        try {
            val queue = connection.getSendMessageQueue()
            while (connection.isConnected) {
                val msg = queue.take()
                socket.println(msg)
            }
        } catch (e: Exception) {
            connection.onError(e)
        }

    }
}
package reactive.networks

import utils.Connection

class InThread(
        val socket: ISocket,
        val connection: Connection
) : Thread() {
    override fun run() {
        try {
            while (connection.isConnected) {
                connection.onNewMessage(socket.readLine())
            }
        } catch (e: Exception) {
            connection.onError(e)
        }

    }
}
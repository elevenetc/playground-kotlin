package reactive.networks

import utils.ConnectionHandler

class InThread(
        val socket: ISocket,
        val connectionHandler: ConnectionHandler
) : Thread() {

    init {
        isDaemon = true
    }

    override fun run() {
        try {
            while (connectionHandler.isConnected) {
                connectionHandler.onNewMessage(socket.readLine())
            }
        } catch (e: Exception) {
            connectionHandler.onError(e)
        }

    }
}
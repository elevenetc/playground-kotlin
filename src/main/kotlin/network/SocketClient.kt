package network

import threading.Scheduler


class SocketClient(
        val handler: ConnectionHandler,
        val socketFactory: SocketFactory,
        val scheduler: Scheduler,
        val reconnectionRetryDelay: Long = 1000,
        var connectAttempts: Int = -1) {

    fun connect() {
        scheduler.thread {
            internalConnect()
        }.start()
    }

    fun internalConnect() {
        try {
            Connection(socketFactory.clientSocket(), handler).connect()
            println("client: connected")
        } catch (e: Exception) {

            println("client: error connection")
            e.printStackTrace()

            reconnectIfNeeded()
        }
    }

    private fun reconnectIfNeeded() {

        var reconnect: Boolean = false
        if (connectAttempts == -1) {
            reconnect = true
        } else if (connectAttempts > 0) {
            connectAttempts--
            reconnect = true

        }

        if (reconnect) {
            scheduler.sleepFor(reconnectionRetryDelay)
            internalConnect()
        }
    }
}
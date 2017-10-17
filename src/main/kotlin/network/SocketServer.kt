package network

import threading.Scheduler

class SocketServer(
        val connectionHandlerFactory: ConnectionHandlerFactory,
        val socketFactory: SocketFactory,
        val scheduler: Scheduler
) {

    fun start() {

        val serverSocket = socketFactory.serverSocket()

        scheduler.thread({
            Connection(serverSocket.accept(), connectionHandlerFactory.create()).connect()
            println("server: a client connected")
        }).start()

    }
}
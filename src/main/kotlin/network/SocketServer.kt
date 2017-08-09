package network

import org.slf4j.LoggerFactory
import threading.Scheduler

class SocketServer(
        val connectionFactory: ConnectionHandlerFactory,
        val socketFactory: SocketFactory,
        val scheduler: Scheduler
) {

    val logger = LoggerFactory.getLogger(SocketServer::class.java)!!

    @Volatile
    var isRunning: Boolean = true

    fun start() {

        val serverSocket = socketFactory.serverSocket()

        scheduler.thread({
            var num: Int = 0

            while (isRunning) {

                num++

                logger.info("waiting for a client")

                val clientSocket = serverSocket.accept()

                val connection = connectionFactory.create()
                connection.isConnected = true

                logger.info("client($num) is connected")
                scheduler.thread({ outFun(clientSocket, connection) }).start()
                scheduler.thread({ inFun(clientSocket, connection) }).start()

                connection.onReady()

            }
        }).start()

    }
}
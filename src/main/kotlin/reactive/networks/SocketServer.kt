package reactive.networks

import org.slf4j.LoggerFactory
import utils.Connection

class SocketServer(
        val port: Int,
        val connection: Connection,
        val socketFactory: SocketFactory
) {

    val logger = LoggerFactory.getLogger(SocketServer::class.java)!!

    @Volatile
    var isRunning: Boolean = false

    fun start() {

        val serverSocket = socketFactory.serverSocket()

        Thread({

            var num: Int = 0

            while (isRunning) {

                num++

                logger.info("waiting for a client")

                val clientSocket = serverSocket.accept()

                logger.info("client($num) is connected")
                OutThread(clientSocket, connection).start()
                InThread(clientSocket, connection).start()

                connection.onReady()

            }
        }).start()

    }
}
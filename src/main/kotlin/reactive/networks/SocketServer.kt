package reactive.networks

import org.slf4j.LoggerFactory

class SocketServer(
        val connectionFactory: ConnectionHandlerFactory,
        val socketFactory: SocketFactory
) {

    val logger = LoggerFactory.getLogger(SocketServer::class.java)!!

    @Volatile
    var isRunning: Boolean = true

    fun start() {

        val serverSocket = socketFactory.serverSocket()

        val thread = Thread({


            var num: Int = 0

            while (isRunning) {

                num++

                logger.info("waiting for a client")

                val clientSocket = serverSocket.accept()

                val connection = connectionFactory.create()
                connection.isConnected = true

                logger.info("client($num) is connected")
                val outThread = OutThread(clientSocket, connection)
                val inThread = InThread(clientSocket, connection)

                outThread.name = "out-server"
                inThread.name = "in-server"

                outThread.start()
                inThread.start()

                connection.onReady()

            }
        })
        thread.isDaemon = true
        thread.start()

    }
}
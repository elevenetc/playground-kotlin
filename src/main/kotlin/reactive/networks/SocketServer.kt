package reactive.networks

import org.slf4j.LoggerFactory
import utils.Connection
import java.net.ServerSocket

class SocketServer(
        val port: Int,
        val connection: Connection
) {

    val logger = LoggerFactory.getLogger(SocketServer::class.java)!!
    val connectionState: ConnectionState = ConnectionState()

    fun start() {
        connectionState.isRunning = true
        val serverSocket = ServerSocket(port)

        Thread({

            var num: Int = 0

            while (connectionState.isRunning) {

                logger.info("waiting for a client")

                val clientSocket = serverSocket.accept()
                num++
                logger.info("client($num) is connected")
                OutThread(connectionState, clientSocket, connection).start()
                InThread(connectionState, clientSocket, connection).start()

                connection.onConnected()
            }
        }).start()

    }
}
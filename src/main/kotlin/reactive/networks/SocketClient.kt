package reactive.networks

import org.slf4j.LoggerFactory
import utils.ConnectionHandler


class SocketClient(
        val connectionHandler: ConnectionHandler,
        val socketFactory: SocketFactory,
        val reconnectionRetryDelay: Long = 1000) {

    val logger = LoggerFactory.getLogger(SocketClient::class.java)!!
    var inThread: InThread? = null
    var outThread: OutThread? = null

    fun connect() {

        Thread({

            logger.info("created")
            connectionHandler.isConnected = true

            while (connectionHandler.isConnected) {
                try {

                    val socket = socketFactory.clientSocket()

                    inThread = InThread(socket, connectionHandler)
                    outThread = OutThread(socket, connectionHandler)

                    inThread?.name = "in-client"
                    outThread?.name = "out-client"

                    inThread?.start()
                    outThread?.start()

                    connectionHandler.onReady()

                    Thread.sleep(Long.MAX_VALUE)
                } catch (e: Exception) {
                    logger.info("connectionHandler error. reconnection in $reconnectionRetryDelay ms")
                    inThread?.interrupt()
                    outThread?.interrupt()
                    Thread.sleep(reconnectionRetryDelay)
                }
            }


        }).start()
    }
}
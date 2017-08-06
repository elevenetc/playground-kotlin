package reactive.networks

import org.slf4j.LoggerFactory
import utils.Connection


class SocketClient(
        val host: String,
        val port: Int,
        val connection: Connection,
        val socketFactory: SocketFactory,
        val reconnectionRetryDelay: Long = 1000) {

    val logger = LoggerFactory.getLogger(SocketClient::class.java)!!
    var inThread: InThread? = null
    var outThread: OutThread? = null

    fun connect() {

        Thread({

            logger.info("created")
            connection.isConnected = true

            while (connection.isConnected) {
                try {

                    val socket = socketFactory.clientSocket()

                    inThread = InThread(socket, connection)
                    outThread = OutThread(socket, connection)

                    inThread?.start()
                    outThread?.start()

                    connection.onReady()

                    Thread.sleep(Long.MAX_VALUE)
                } catch (e: Exception) {
                    logger.info("connection error. reconnection in $reconnectionRetryDelay ms")
                    inThread?.interrupt()
                    outThread?.interrupt()
                    Thread.sleep(reconnectionRetryDelay)
                }
            }


        }).start()
    }
}
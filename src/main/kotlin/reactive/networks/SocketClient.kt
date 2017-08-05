package reactive.networks

import org.slf4j.LoggerFactory
import utils.Connection
import java.net.Socket


class SocketClient(
        val host: String,
        val port: Int,
        val connection: Connection,
        val reconnectionRetryDelay: Long = 1000) {

    val logger = LoggerFactory.getLogger(SocketClient::class.java)!!
    val connectionState: ConnectionState = ConnectionState()
    var inThread: InThread? = null
    var outThread: OutThread? = null

    fun connect() {

        Thread({

            logger.info("created")
            connectionState.isRunning = true

            while (connectionState.isRunning) {
                try {

                    val socket = Socket(host, port)



                    inThread = InThread(connectionState, socket, connection)
                    outThread = OutThread(connectionState, socket, connection)

                    inThread?.start()
                    outThread?.start()

                    connection.onConnected()

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

    fun log(msg: String) {
        println("client: $msg")
    }
}
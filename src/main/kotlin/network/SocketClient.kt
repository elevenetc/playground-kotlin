package network

import org.slf4j.LoggerFactory
import threading.IThread
import threading.Scheduler


class SocketClient(
        val connection: Connection,
        val socketFactory: SocketFactory,
        val scheduler: Scheduler,
        val reconnectionRetryDelay: Long = 1000) {

    val logger = LoggerFactory.getLogger(SocketClient::class.java)!!
    var inThread: IThread? = null
    var outThread: IThread? = null

    fun connect() {

        scheduler.thread({

            logger.info("created")
            connection.isConnected = true

            while (connection.isConnected) {
                try {

                    val socket = socketFactory.clientSocket()

                    inThread = scheduler.thread { inFun(socket, connection) }
                    outThread = scheduler.thread { outFun(socket, connection) }

                    inThread?.start()
                    outThread?.start()

                    logger.info("connected")

                    connection.onReady()

                    scheduler.sleepForever()

                } catch (e: Exception) {
                    logger.info("connection error. reconnection in $reconnectionRetryDelay ms")
                    inThread?.interrupt()
                    outThread?.interrupt()

                    scheduler.sleepFor(reconnectionRetryDelay)
                }
            }


        }).start()
    }
}
package network

import threading.BasicScheduler

fun runPingPong() {
    val port = 9999
    val host = "localhost"

    val currentThread = Thread.currentThread()
    val daemon = currentThread.isDaemon

    org.apache.log4j.BasicConfigurator.configure()

    val socketFactory = JavaNetSocketFactory(host, port)


    SocketServer(object : ConnectionHandlerFactory {
        override fun create(): Connection {
            return ServerHandler()
        }
    }, socketFactory, BasicScheduler()).start()
    Thread.sleep(2000)
    SocketClient(ClientHandler(), socketFactory, BasicScheduler()).connect()

    Thread.sleep(Long.MAX_VALUE)
}

class ClientHandler : Connection() {


    override fun onReady() {
        while (true) {
            sendMessage("ping")
            Thread.sleep(1000)
        }
    }

    override fun onNewMessage(message: String) {

    }
}

class ServerHandler : Connection() {
    override fun onNewMessage(message: String) {
        if (message == "ping") {
            sendMessage("pong")
        }
    }
}
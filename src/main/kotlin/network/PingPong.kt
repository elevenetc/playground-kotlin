package network

import threading.BasicScheduler

fun runPingPong() {
    val port = 9999
    val host = "localhost"

    org.apache.log4j.BasicConfigurator.configure()

    val socketFactory = JavaNetSocketFactory(host, port)


    SocketServer(object : ConnectionHandlerFactory {
        override fun create(): ConnectionHandler {
            return ServerConnectionHandler()
        }
    }, socketFactory, BasicScheduler()).start()

    Thread.sleep(2000)

    SocketClient(ClientConnectionHandler(), socketFactory, BasicScheduler()).connect()

    Thread.sleep(Long.MAX_VALUE)
}

class ClientConnectionHandler : ConnectionHandler() {
    override fun onReady() {
        println("client: onReady")
        sendMessage("ping")
    }

    override fun onMessage(message: String) {
        println("client: onMessage - $message")
        //check of pong arrived
    }
}

class ServerConnectionHandler : ConnectionHandler() {

    override fun onMessage(message: String) {
        println("server: onMessage - $message")
        if (message == "ping") {
            sendMessage("pong")
        }
    }
}
package main

import reactive.networks.JavaNetSocketFactory
import reactive.networks.SocketClient
import reactive.networks.SocketServer
import utils.Connection

class Main

fun main(args: Array<String>) {
    val port = 9999
    val host = "localhost"
    org.apache.log4j.BasicConfigurator.configure()

    val socketFactory = JavaNetSocketFactory(host, port)


    SocketClient(host, port, ClientConnection(), socketFactory).connect()

    Thread.sleep(2000)
    SocketServer(port, ServerConnection(), socketFactory).start()

    Thread.sleep(Long.MAX_VALUE)
}

class ClientConnection : Connection() {


    override fun onReady() {
        while (true) {
            sendMessage("ping")
            Thread.sleep(1000)
        }
    }

    override fun onNewMessage(message: String) {

    }
}

class ServerConnection : Connection() {
    override fun onNewMessage(message: String) {
        if (message == "ping") {
            sendMessage("pong")
        }
    }
}
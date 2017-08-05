package main

import reactive.networks.SocketClient
import reactive.networks.SocketServer
import utils.Connection

class Main

fun main(args: Array<String>) {
    val port = 9999
    org.apache.log4j.BasicConfigurator.configure()

    SocketClient("localhost", port, ClientConnection()).connect()

    Thread.sleep(2000)
    SocketServer(port, ServerConnection()).start()

    Thread.sleep(Long.MAX_VALUE)
}

class ClientConnection : Connection() {



    override fun onConnected() {
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
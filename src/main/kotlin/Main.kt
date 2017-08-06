package main

import reactive.networks.ConnectionHandlerFactory
import reactive.networks.JavaNetSocketFactory
import reactive.networks.SocketClient
import reactive.networks.SocketServer
import utils.ConnectionHandler

class Main

fun main(args: Array<String>) {
    val port = 9999
    val host = "localhost"

    val currentThread = Thread.currentThread()
    val daemon = currentThread.isDaemon

    org.apache.log4j.BasicConfigurator.configure()

    val socketFactory = JavaNetSocketFactory(host, port)


    SocketServer(object : ConnectionHandlerFactory {
        override fun create(): ConnectionHandler {
            return ServerHandler()
        }
    }, socketFactory).start()
    Thread.sleep(2000)
    SocketClient(ClientHandler(), socketFactory).connect()

    Thread.sleep(Long.MAX_VALUE)
}

class ClientHandler : ConnectionHandler() {


    override fun onReady() {
        while (true) {
            sendMessage("ping")
            Thread.sleep(1000)
        }
    }

    override fun onNewMessage(message: String) {

    }
}

class ServerHandler : ConnectionHandler() {
    override fun onNewMessage(message: String) {
        if (message == "ping") {
            sendMessage("pong")
        }
    }
}
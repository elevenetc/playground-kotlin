package main

import reactive.networks.SocketClient
import reactive.networks.SocketServer

class Main

fun main(args: Array<String>) {
    val port = 9999
    //SocketServer(port).start()
    SocketClient("localhost", port).connect()
    Thread.sleep(Long.MAX_VALUE)
}
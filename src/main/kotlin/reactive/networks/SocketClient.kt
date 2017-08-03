package reactive.networks

import java.net.Socket


class SocketClient(val host: String, val port: Int) {

    val connectionState: ConnectionState = ConnectionState()

    fun connect() {

        Thread({

            connectionState.isRunning = true

            println("client is created")
            val socket = Socket(host, port)
            println("client is connected")
            InThread(connectionState, socket).start()
            OutThread(connectionState, socket).start()

        }).start()
    }
}
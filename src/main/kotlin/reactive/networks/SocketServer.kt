package reactive.networks

import java.net.ServerSocket


class SocketServer(val port: Int) {

    val connectionState: ConnectionState = ConnectionState()

    fun start() {
        connectionState.isRunning = true
        val serverSocket = ServerSocket(port)

        Thread({
            while (connectionState.isRunning) {
                println("waiting for a client")
                val clientSocket = serverSocket.accept()
                println("client is connected")
                OutThread(connectionState, clientSocket).start()
                InThread(connectionState, clientSocket).start()
            }
        }).start()

    }

}
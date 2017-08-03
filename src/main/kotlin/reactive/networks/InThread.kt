package reactive.networks

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.Socket

class InThread(val connectionState: ConnectionState, val socket: Socket) : Thread() {
    override fun run() {
        val input = BufferedReader(InputStreamReader(socket.getInputStream()))
        while (connectionState.isRunning) {

            println("received from client:" + input.readLine())
        }
    }
}
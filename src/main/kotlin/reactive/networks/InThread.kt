package reactive.networks

import utils.Connection
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.Socket

class InThread(
        val connectionState: ConnectionState,
        val socket: Socket,
        val readWriteHandle: Connection
) : Thread() {
    override fun run() {
        val input = BufferedReader(InputStreamReader(socket.getInputStream()))
        while (connectionState.isRunning) {
            readWriteHandle.onNewMessage(input.readLine())
        }
    }
}
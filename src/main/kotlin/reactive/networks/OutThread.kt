package reactive.networks

import utils.Connection
import java.io.PrintWriter
import java.net.Socket

class OutThread(
        val connectionState: ConnectionState,
        val socket: Socket,
        val readWriteHandle: Connection) : Thread() {

    override fun run() {
        val out = PrintWriter(socket.getOutputStream(), true)
        val queue = readWriteHandle.getSendMessageQueue()
        while (connectionState.isRunning) {
            val msg = queue.take()
            out.println(msg)
        }
    }
}
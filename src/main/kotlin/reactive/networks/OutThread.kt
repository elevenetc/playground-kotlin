package reactive.networks

import java.io.PrintWriter
import java.net.Socket
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingDeque

class OutThread(val connectionState: ConnectionState, val socket: Socket) : Thread() {

    val queue: BlockingQueue<String> = LinkedBlockingDeque()

    override fun run() {
        val out = PrintWriter(socket.getOutputStream(), true)
        while (connectionState.isRunning) {
            val msg = queue.take()
            out.println(msg)
            println("sent : $msg")
        }
    }

    fun sendMessage(msg: String) {
        queue.add(msg)
    }
}
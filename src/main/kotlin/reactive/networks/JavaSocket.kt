package reactive.networks

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class JavaSocket(val socket: Socket) : ISocket {

    val input: BufferedReader by lazy { BufferedReader(InputStreamReader(socket.getInputStream())) }
    val out: PrintWriter by lazy { PrintWriter(socket.getOutputStream(), true) }

    override fun readLine(): String {
        return input.readLine()
    }

    override fun println(str: String) {
        out.println(str)
    }
}
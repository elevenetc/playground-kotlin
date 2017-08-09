package utils

import network.ISocket

class MockClientSocket(val message: String) : ISocket {

    override fun read(): String {
        return message
    }

    override fun write(data: String) {

    }
}
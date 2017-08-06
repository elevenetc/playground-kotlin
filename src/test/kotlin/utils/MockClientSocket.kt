package utils

import reactive.networks.ISocket

class MockClientSocket(val message: String) : ISocket {

    override fun read(): String {
        return message
    }

    override fun write(data: String) {

    }
}
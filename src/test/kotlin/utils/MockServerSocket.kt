package utils

import org.mockito.Mockito
import reactive.networks.IServerSocket
import reactive.networks.ISocket

class MockServerSocket(val socket: ISocket) : IServerSocket {

    var returned: Boolean = false

    override fun accept(): ISocket {
        if (!returned) {
            returned = true
            return socket
        } else {
            sleepForever()
            return Mockito.mock(ISocket::class.java)
        }
    }
}
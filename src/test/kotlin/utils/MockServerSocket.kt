package utils

import org.mockito.Mockito
import network.IServerSocket
import network.ISocket

class MockServerSocket(val socket: ISocket) : IServerSocket {

    var returned: Boolean = false

    override fun accept(): ISocket {
        if (!returned) {
            returned = true
            return socket
        } else {
            //sleepForever()
            return Mockito.mock(ISocket::class.java)
        }
    }
}
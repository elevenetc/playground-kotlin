package utils

import network.IServerSocket
import network.ISocket
import network.SocketFactory
import org.mockito.Mockito
import java.util.*

/**
 * SocketFactory which emulates client server interaction.
 * All data which is sent by client(socket) is received by server(socket) and vice versa.
 */
class BasicConnectionSocketFactory : SocketFactory {

    val serverQueue: Deque<String> = LinkedList()
    val clientQueue: Deque<String> = LinkedList()

    override fun clientSocket(): ISocket {
        return object : ISocket {

            override fun read(): String {
                return clientQueue.first
            }

            override fun write(data: String) {
                serverQueue.add(data)
            }

        }
    }

    /**
     * Returns server socket which accepts only one client
     */
    override fun serverSocket(): IServerSocket {
        return object : IServerSocket {

            @Volatile
            var returned: Boolean = false

            override fun accept(): ISocket {

                if (returned) {
                    sleepForever()
                    return Mockito.mock(ISocket::class.java)
                } else {

                    returned = true

                    return object : ISocket {

                        override fun read(): String {
                            return serverQueue.first
                        }

                        override fun write(data: String) {
                            clientQueue.add(data)
                        }

                    }
                }

            }

        }
    }

}
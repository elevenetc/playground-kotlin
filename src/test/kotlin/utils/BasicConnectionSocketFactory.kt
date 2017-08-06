package utils

import org.mockito.Mockito
import reactive.networks.IServerSocket
import reactive.networks.ISocket
import reactive.networks.SocketFactory
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingDeque

/**
 * SocketFactory which emulates client server interaction.
 * All data which is sent by client(socket) is received by server(socket) and vice versa.
 */
class BasicConnectionSocketFactory : SocketFactory {

    val serverQueue: BlockingQueue<String> = LinkedBlockingDeque()
    val clientQueue: BlockingQueue<String> = LinkedBlockingDeque()

    override fun clientSocket(): ISocket {
        return object : ISocket {

            override fun read(): String {
                return clientQueue.take()
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
                            return serverQueue.take()
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
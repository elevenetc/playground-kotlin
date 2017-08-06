package reactive.networks

interface SocketFactory {
    fun clientSocket(): ISocket
    fun serverSocket(): IServerSocket
}
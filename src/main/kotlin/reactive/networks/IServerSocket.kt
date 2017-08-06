package reactive.networks

interface IServerSocket {
    fun accept(): ISocket
}
package network

interface ConnectionHandlerFactory {
    fun create(): ConnectionHandler
}
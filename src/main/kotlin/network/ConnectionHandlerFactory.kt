package network

interface ConnectionHandlerFactory {
    fun create(): Connection
}
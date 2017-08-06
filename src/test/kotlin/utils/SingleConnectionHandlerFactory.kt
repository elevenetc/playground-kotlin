package utils

import reactive.networks.ConnectionHandlerFactory

class SingleConnectionHandlerFactory(val connectionHandler: ConnectionHandler) : ConnectionHandlerFactory {
    override fun create(): ConnectionHandler {
        return connectionHandler
    }
}
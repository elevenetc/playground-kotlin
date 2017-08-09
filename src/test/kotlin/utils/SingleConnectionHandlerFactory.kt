package utils

import network.Connection
import network.ConnectionHandlerFactory

class SingleConnectionHandlerFactory(val connectionHandler: Connection) : ConnectionHandlerFactory {
    override fun create(): Connection {
        return connectionHandler
    }
}
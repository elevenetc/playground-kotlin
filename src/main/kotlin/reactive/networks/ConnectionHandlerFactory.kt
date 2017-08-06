package reactive.networks

import utils.ConnectionHandler

interface ConnectionHandlerFactory {
    fun create(): ConnectionHandler
}
package network

val outFun = fun(socket: ISocket, connection: Connection) {

    try {
        while (connection.isConnected) {
            socket.write(connection.nextMessageToSend())
        }
    } catch (e: Exception) {
        connection.onError(e)
    }
}

val inFun = fun(socket: ISocket, connection: Connection) {
    try {
        while (connection.isConnected) {
            connection.onNewMessage(socket.read())
        }
    } catch (e: Exception) {
        connection.onError(e)
    }
}
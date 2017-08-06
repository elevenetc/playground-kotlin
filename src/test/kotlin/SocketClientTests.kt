import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import reactive.networks.SocketClient
import reactive.networks.SocketFactory
import reactive.networks.SocketServer
import utils.ConnectionHandler
import utils.SingleConnectionHandlerFactory
import utils.SingleSocketFactory
import utils.sleepFor

class SocketClientTests {

    @Before fun before() {
        org.apache.log4j.BasicConfigurator.configure()
    }

    @Test fun testConnection() {


        val clientConnection = spy(ConnectionHandler())
        val serverConnection = spy(ConnectionHandler())

        val socketFactory: SocketFactory = SingleSocketFactory()
        val serverSocketFactory = SingleConnectionHandlerFactory(serverConnection)

        `when`(clientConnection.onReady()).then {
            clientConnection.sendMessage("ping")
        }

        `when`(serverConnection.onNewMessage("ping")).then {
            serverConnection.sendMessage("pong")
        }

        SocketServer(serverSocketFactory, socketFactory).start()
        SocketClient(clientConnection, socketFactory).connect()

        sleepFor(2000)

        `verify`(clientConnection).onNewMessage("pong")
        `verify`(serverConnection).onNewMessage("ping")

        `verify`(clientConnection, never()).onError(Exception())
        `verify`(serverConnection, never()).onError(Exception())
    }
}

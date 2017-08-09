import network.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import threading.BasicScheduler
import threading.Scheduler
import utils.SingleConnectionHandlerFactory
import utils.sleepFor

class SocketClientTests {

    lateinit var scheduler: Scheduler
    lateinit var socketFactory: SocketFactory

    @Before fun before() {
        org.apache.log4j.BasicConfigurator.configure()
        scheduler = BasicScheduler()
        socketFactory = JavaNetSocketFactory("localhost", 9999)
    }

    @Test fun testPingPong() {


        val clientConnection = spy(Connection())
        val serverConnection = spy(Connection())

        `when`(clientConnection.onReady()).then {
            clientConnection.sendMessage("ping")
        }

//        `when`(serverConnection.onNewMessage("ping")).then {
//            serverConnection.sendMessage("pong")
//        }

        SocketServer(SingleConnectionHandlerFactory(serverConnection), socketFactory, scheduler).start()
        SocketClient(clientConnection, socketFactory, scheduler).connect()

        sleepFor(5000)

        `verify`(clientConnection).onNewMessage("pong")
//        `verify`(serverConnection).onNewMessage("ping")
//
//        `verify`(clientConnection, never()).onError(Exception())
//        `verify`(serverConnection, never()).onError(Exception())
    }
}

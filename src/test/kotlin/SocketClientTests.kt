import org.junit.Test
import reactive.networks.SocketClient

class SocketClientTests {
    @Test fun testConnection() {
        val testObserver = SocketClient("").connect().test()
        testObserver.awaitTerminalEvent()
        testObserver.assertNoErrors()
    }
}


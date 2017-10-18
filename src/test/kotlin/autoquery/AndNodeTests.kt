package autoquery

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AndNodeTests {
    @Test
    fun andNodeTests() {
        val node = AndNode(listOf("hello", "bye"))
        node.append('h')
        node.complete()
        assertEquals("hello", node.simpleName())
        node.append('b')
        node.append('y')
        node.append('e')
        assertEquals("hello, bye", node.simpleName())
        assertTrue(node.isCompleted)
    }

}
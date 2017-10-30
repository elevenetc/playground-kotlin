package autoquery

import autoquery.nodes.SingleNode
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class SingleNodeTests {
    @Test
    fun testNotComplete() {
        val node = SingleNode("hello")
        node.complete()
        assertFalse(node.isCompleted())

        node.addChar('h')
        node.addChar('e')
        node.addChar('l')
        node.addChar('l')
        node.addChar('o')
        assertFalse(node.isCompleted())
        node.addChar('z')
        assertFalse(node.isCompleted())
    }

    @Test
    fun testDelete() {
        val node = SingleNode("hello")
        node.setCompleted("hello")
        node.deleteChar()
        assertEquals("hell", node.toQuery())
        node.deleteChar()
        assertEquals("hel", node.toQuery())
        node.deleteChar()
        assertEquals("he", node.toQuery())
        node.deleteChar()
        assertEquals("h", node.toQuery())
        node.deleteChar()
        assertEquals("", node.toQuery())
        node.deleteChar()
        assertEquals("", node.toQuery())
    }
}
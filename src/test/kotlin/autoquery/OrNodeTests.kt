package autoquery

import autoquery.nodes.OrNode
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class OrNodeTests {
    @Test
    fun test1() {
        val node = OrNode(listOf("hello", "bye"))
        node.addChar('h')
        node.complete()
        assertTrue(node.isCompleted())
    }

    @Test
    fun test2() {
        val node = OrNode(listOf("hello", "bye"))
        node.addChar('x')
        node.complete()
        assertFalse(node.isCompleted())
    }

    @Test
    fun test3() {
        val node = OrNode(listOf("hello", "bye"))
        node.addChar('h')
        node.addChar('e')
        node.addChar('l')
        node.addChar('l')
        node.addChar('o')
        assertFalse(node.isCompleted())
        node.complete()
        assertTrue(node.isCompleted())
    }

    @Test
    fun deleteTest() {
        val node = OrNode(listOf("hello", "bye"))
        node.addChar('b')
        node.addChar('y')
        node.addChar('e')
        node.complete()
        node.deleteChar()
        assertEquals("by", node.toQuery())
        node.deleteChar()
        assertEquals("b", node.toQuery())
        node.deleteChar()
        assertEquals("", node.toQuery())
    }
}
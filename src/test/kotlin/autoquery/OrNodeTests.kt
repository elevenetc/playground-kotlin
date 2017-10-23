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
        node.append('h')
        node.complete()
        assertTrue(node.isCompleted())
    }

    @Test
    fun test2() {
        val node = OrNode(listOf("hello", "bye"))
        node.append('x')
        node.complete()
        assertFalse(node.isCompleted())
    }

    @Test
    fun test3() {
        val node = OrNode(listOf("hello", "bye"))
        node.append('h')
        node.append('e')
        node.append('l')
        node.append('l')
        node.append('o')
        assertFalse(node.isCompleted())
        node.complete()
        assertTrue(node.isCompleted())
    }

    @Test
    fun deleteTest() {
        val node = OrNode(listOf("hello", "bye"))
        node.append('b')
        node.append('y')
        node.append('e')
        node.complete()
        node.delete()
        assertEquals("by", node.toQuery())
        node.delete()
        assertEquals("b", node.toQuery())
        node.delete()
        assertEquals("", node.toQuery())
    }
}
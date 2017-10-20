package autoquery

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AndNodeTests {
    @Test
    fun test1() {
        val node = AndNode(listOf("hello", "bye"))
        node.append('h')
        node.complete()
        assertEquals("hello", node.toQuery())
        node.append('b')
        node.append('y')
        node.append('e')
        assertEquals("hello bye", node.toQuery())
        assertTrue(node.isCompleted)
    }

    @Test
    fun testComma() {
        val node = AndNode(listOf("abc", "cde"))
        node.append('a')
        node.append(',')
        node.append('c')
        node.complete()
        assertEquals("abc cde", node.toQuery())
    }

}
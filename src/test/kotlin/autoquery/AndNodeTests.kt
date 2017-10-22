package autoquery

import autoquery.nodes.AndNode
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
        assertTrue(node.isCompleted())
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

    @Test
    fun deleteChars() {
        val node = fillHelloBye()
        node.delete()
        assertEquals("hello by", node.toQuery())
        node.delete()
        assertEquals("hello b", node.toQuery())
        node.delete()
        assertEquals("hello", node.toQuery())
        node.delete()
        assertEquals("hell", node.toQuery())
        node.delete()
        assertEquals("hel", node.toQuery())
        node.delete()
        assertEquals("he", node.toQuery())
        node.delete()
        assertEquals("h", node.toQuery())
        node.delete()
        assertEquals("", node.toQuery())
        node.delete()
        assertEquals("", node.toQuery())
    }

    fun fillHelloBye(): AndNode {
        val node = AndNode(listOf("hello", "bye"))
        node.append('h')
        node.complete()
        node.append('b')
        node.complete()
        return node
    }

}
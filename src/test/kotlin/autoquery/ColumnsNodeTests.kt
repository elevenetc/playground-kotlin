package autoquery

import autoquery.nodes.ColumnsNode
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ColumnsNodeTests {

    @Test
    fun test1() {
        val node = ColumnsNode(listOf("hello", "bye"))
        node.addChar('h')
        node.complete()
        assertEquals("hello", node.toQuery())
        node.addChar(',')
        node.addChar('b')
        node.complete()
        assertEquals("hello, bye", node.toQuery())
        assertTrue(node.isCompleted())
    }

    @Test
    fun testComma() {
        val node = ColumnsNode(listOf("abc", "cde"))
        node.addChar('a')
        node.addChar(',')
        node.addChar('c')
        node.complete()
        assertEquals("abc, cde", node.toQuery())
    }

    @Test
    fun deleteChars1() {
        val node = fillHelloBye()
        node.deleteChar()
        assertEquals("hello by", node.toQuery())
        node.deleteChar()
        assertEquals("hello b", node.toQuery())
        node.deleteChar()
        assertEquals("hello", node.toQuery())
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

    @Test
    fun deleteChars2() {
        val node = ColumnsNode(listOf("aaa", "bbb"))
        node.addChar('a')
        node.complete()
        assertEquals("aaa", node.toQuery())
        node.addChar(',')
        assertEquals("aaa, ", node.toQuery())
    }


    private fun fillHelloBye(): ColumnsNode {
        val node = ColumnsNode(listOf("hello", "bye"))
        node.addChar('h')
        node.complete()
        node.addChar('b')
        node.complete()
        return node
    }

}
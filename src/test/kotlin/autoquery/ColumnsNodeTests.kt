package autoquery

import autoquery.nodes.ColumnsNode
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ColumnsNodeTests {

    @Test
    fun test1() {
        val node = ColumnsNode(listOf("hello", "bye"))
        node.append('h')
        node.complete()
        assertEquals("hello", node.toQuery())
        node.append('b')
        node.append('y')
        node.append('e')
        assertEquals("hello, bye", node.toQuery())
        assertTrue(node.isCompleted())
    }

    @Test
    fun testComma() {
        val node = ColumnsNode(listOf("abc", "cde"))
        node.append('a')
        node.append(',')
        node.append('c')
        node.complete()
        assertEquals("abc, cde", node.toQuery())
    }

    @Test
    fun deleteChars1() {
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

    @Test
    fun deleteChars2() {
        val node = ColumnsNode(listOf("aaa", "bbb"))
        node.append('a')
        node.complete()
        assertEquals("aaa", node.toQuery())
        node.append(',')
        assertEquals("aaa, ", node.toQuery())
    }


    private fun fillHelloBye(): ColumnsNode {
        val node = ColumnsNode(listOf("hello", "bye"))
        node.append('h')
        node.complete()
        node.append('b')
        node.complete()
        return node
    }

}
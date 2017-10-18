package utils

import autoquery.*
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AutoTreeTests {
    @Test
    fun hello() {

        val query: Query = SelectQuery(
                Table("students", int("age"), string("name")),
                Table("teachers", string("subject"))
        )

        assertEquals(0, query.currentIndex())

        query.append('s')
        query.complete()

        assertEquals("select", query.getCurrent()[0].simpleName())
        assertEquals(1, query.currentIndex())

        query.append('n')
        query.append('a')
        query.complete()

        assertEquals(2, query.currentIndex())
        assertEquals("select", query.getCurrent()[0].simpleName())
        assertEquals("name", query.getCurrent()[1].simpleName())
        assertEquals("select, name", query.getSimpleName())
    }

    @Test
    fun testCompletability() {
        assertEquals(0, countCompletable("", "z"))
        assertEquals(0, countCompletable("hello", ""))
        assertEquals(0, countCompletable("hello", "helloz"))

        assertEquals(1, countCompletable("hello", "h"))
        assertEquals(2, countCompletable("hello", "he"))
        assertEquals(3, countCompletable("hello", "hel"))
        assertEquals(5, countCompletable("hello", "hello"))
    }

    @Test
    fun testLongestCompletable() {
        assertEquals("hello", getShortestCompletable("he", listOf("hello", "zello")))
        assertEquals("", getShortestCompletable("f", listOf("hello", "zello")))
        assertEquals("hello", getShortestCompletable("hell", listOf("hello", "hello bob")))

        //ignore cases
        assertEquals("", getShortestCompletable("b", listOf("aaa", "bbb", "ccc"), listOf("bbb")))
        assertEquals("bbb", getShortestCompletable("b", listOf("b", "bb", "bbb"), listOf("b", "bb")))
    }

    @Test
    fun testFullCompletable() {
        assertEquals(0, getFullCompletableIndex("hello", listOf("hello", "hello-a")))
        assertEquals(-1, getFullCompletableIndex("hello", listOf("ffff", "hello-a")))
        //ignore cases
        assertEquals(-1, getFullCompletableIndex("hello", listOf("bye", "hello"), listOf("hello")))
        assertEquals(-1, getFullCompletableIndex("zed", listOf("bye", "hello"), listOf("hello")))
    }

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

    @Test
    fun orNodeTests1() {
        val node = OrNode(listOf("hello", "bye"))
        node.append('h')
        node.complete()
        assertTrue(node.isCompleted)
    }

    @Test
    fun orNodeTests2() {
        val node = OrNode(listOf("hello", "bye"))
        node.append('x')
        node.complete()
        assertFalse(node.isCompleted)
    }

}
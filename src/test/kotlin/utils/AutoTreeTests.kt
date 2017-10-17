package utils

import autoquery.*
import org.junit.Test
import kotlin.test.assertEquals

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
        assertEquals("hello", getShortestCompletable("he", "hello", "zello"))
        assertEquals("", getShortestCompletable("f", "hello", "zello"))
        assertEquals("hello", getShortestCompletable("hell", "hello", "hello bob"))
    }

    @Test
    fun testFullCompletable() {
        assertEquals(0, getFullCompletableIndex("hello", "hello", "hello-a"))
        assertEquals(-1, getFullCompletableIndex("hello", "ffff", "hello-a"))
    }
}
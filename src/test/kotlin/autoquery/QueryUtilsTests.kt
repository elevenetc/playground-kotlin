package autoquery

import org.junit.Test
import kotlin.test.assertEquals

class QueryUtilsTests{
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
    fun testCompletability() {
        assertEquals(0, countCompletable("", "z"))
        assertEquals(0, countCompletable("hello", ""))
        assertEquals(0, countCompletable("hello", "helloz"))

        assertEquals(1, countCompletable("hello", "h"))
        assertEquals(2, countCompletable("hello", "he"))
        assertEquals(3, countCompletable("hello", "hel"))
        assertEquals(5, countCompletable("hello", "hello"))
    }
}
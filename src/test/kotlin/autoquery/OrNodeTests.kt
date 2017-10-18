package autoquery

import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class OrNodeTests {
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

    @Test
    fun orNodeTests3() {
        val node = OrNode(listOf("hello", "bye"))
        node.append('h')
        node.append('e')
        node.append('l')
        node.append('l')
        node.append('o')
        assertTrue(node.isCompleted)
    }
}
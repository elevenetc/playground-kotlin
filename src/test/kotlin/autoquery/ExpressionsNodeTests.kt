package autoquery

import org.junit.Test
import kotlin.test.assertEquals

class ExpressionsNodeTests {
    @Test
    fun test01() {
        val ageColumn: Column<*> = int("age")
        val nameColumn: Column<*> = string("name")
        val node = ExpressionsNode(listOf(ageColumn, nameColumn))
        node.append('a')
        node.complete()
        assertEquals("age = ", node.simpleName())
        node.append('1')
        node.append('0')
        assertEquals("age = 10", node.simpleName())
        node.append(',')
        node.append('n')
        node.append('a')
        node.append('m')
        node.append('e')
        node.append('b')
        node.append('o')
        node.append('b')
        assertEquals("age = 10, name = bob", node.simpleName())
    }
}
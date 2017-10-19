package autoquery

import org.junit.Test
import kotlin.test.assertEquals

class ExpressionsNodeTests {
    @Test
    fun integration01() {
        val node = ageAndNameColumns()
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

    @Test
    fun testInvalidName() {
        val node = ageAndNameColumns()
        node.append('z')
        assertEquals("z", node.simpleName())
        node.append('n')
        node.complete()
        assertEquals("zn", node.simpleName())
    }

    @Test
    fun testInvalidValueType() {
        val node = ageAndNameColumns()
        node.append('a')
        node.complete()
        node.append('a')
        assertEquals("age = ", node.simpleName())
    }

    @Test
    fun testAttemptToCompleteValue() {
        val node = ExpressionsNode(listOf(int("age")))
        node.append('a')
        node.complete()
        node.append('1')
        assertEquals("age = 1", node.simpleName())
        node.complete()
        assertEquals("age = 1", node.simpleName())
    }

    @Test
    fun testUnfinishedColumn() {
        val node = ageAndNameColumns()
        node.append('a')
        node.complete()
        node.append('1')
        node.append(',')
        node.append('n')
        node.append('a')
        assertEquals("age = 1, na", node.simpleName())
    }

    private fun ageAndNameColumns(): ExpressionsNode {
        val ageColumn: Column<*> = int("age")
        val nameColumn: Column<*> = string("name")
        return ExpressionsNode(listOf(ageColumn, nameColumn))
    }
}
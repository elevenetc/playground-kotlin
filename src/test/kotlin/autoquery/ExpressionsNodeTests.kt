package autoquery

import org.junit.Test
import kotlin.test.assertEquals

class ExpressionsNodeTests {
    @Test
    fun integration01() {
        val node = ageNameColumns()
        node.append('a')
        node.complete()
        assertEquals("age = ", node.toQuery())
        node.append('1')
        node.append('0')
        assertEquals("age = 10", node.toQuery())
        node.append(',')
        node.append('a')//union
        node.append('n')//union
        node.append('d')//union
        node.append('n')
        node.append('a')
        node.append('m')
        node.append('e')
        node.append('b')
        node.append('o')
        node.append('b')
        assertEquals("age = 10 AND name = bob", node.toQuery())
    }

    @Test
    fun testInvalidName() {
        val node = ageNameColumns()
        node.append('z')
        assertEquals("z", node.toQuery())
        node.append('n')
        node.complete()
        assertEquals("zn", node.toQuery())
    }

    @Test
    fun testInvalidValueType() {
        val node = ageNameColumns()
        node.append('a')
        node.complete()
        node.append('a')
        assertEquals("age = ", node.toQuery())
    }

    @Test
    fun testAttemptToCompleteValue() {
        val node = ExpressionsNode(listOf(int("age")))
        node.append('a')
        node.complete()
        node.append('1')
        assertEquals("age = 1", node.toQuery())
        node.complete()
        assertEquals("age = 1", node.toQuery())
    }

    @Test
    fun testUnfinishedColumn() {
        val node = ageNameColumns()
        node.append('a')
        node.complete()
        node.append('1')
        node.append(',')
        node.append('n')
        node.append('a')
        assertEquals("age = 1", node.toQuery())
    }

    @Test
    fun testAndOr() {
        val node = ageNameIqColumns()
        node.append('a')
        node.complete()
        node.append('1')
        node.append(',')
        node.append('a')//union and
        node.complete()
        node.append('n')
        node.complete()
        node.append('x')
        node.append(',')
        node.append('o')//union or
        node.complete()
        node.append('i')
        node.complete()
        node.append('9')
        assertEquals("age = 1 AND name = x OR iq = 9", node.toQuery())
    }

    private fun ageNameColumns(): ExpressionsNode {
        val ageColumn: Column<*> = int("age")
        val nameColumn: Column<*> = string("name")
        return ExpressionsNode(listOf(ageColumn, nameColumn))
    }

    private fun ageNameIqColumns(): ExpressionsNode {
        val ageColumn: Column<*> = int("age")
        val nameColumn: Column<*> = string("name")
        val iqColumn: Column<*> = int("iq")
        return ExpressionsNode(listOf(ageColumn, nameColumn, iqColumn))
    }
}
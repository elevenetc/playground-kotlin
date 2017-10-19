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
        //TODO should be stored default value in column?
        assertEquals("age = 0", node.simpleName())
    }
}
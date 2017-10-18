package autoquery

import org.junit.Test
import kotlin.test.assertEquals

class ExpressionsNodeTests {
    @Test
    fun test01() {
        val node = ExpressionsNode(listOf(int("age"), string("name")))
        node.append('a')
        node.complete()
        assertEquals("age = ", node.simpleName())
    }
}
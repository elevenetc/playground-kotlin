package autoquery

import autoquery.nodes.ExpressionsGroupNode
import org.junit.Test
import kotlin.test.assertEquals

class ExpressionsGroupNodeTests {
    @Test
    fun appendAndDelete() {
        val node = ExpressionsGroupNode(listOf(
                int("age"),
                string("name")
        ))

        node.append('a')
        node.complete()
        assertEquals("age ", node.toQuery())
        node.append('>')
        node.append('=')
        node.complete()
        node.append('1')

        assertEquals("age >= 1", node.toQuery())

        node.complete()

        node.append('a')
        node.complete()

        assertEquals("age >= 1 and ", node.toQuery())

        node.append('n')
        node.append('a')
        node.append('m')
        node.append('e')

        node.complete()

        node.append('!')
        node.append('=')
        node.complete()

        node.append('z')
        node.append('z')
        node.append('z')

        assertEquals("age >= 1 and name != zzz", node.toQuery())

        node.delete()

        assertEquals("age >= 1 and name != zz", node.toQuery())

        node.delete()

        assertEquals("age >= 1 and name != z", node.toQuery())
        node.delete()
        assertEquals("age >= 1 and name != ", node.toQuery())
        node.delete()
        assertEquals("age >= 1 and name !=", node.toQuery())
        node.delete()
        assertEquals("age >= 1 and name !", node.toQuery())
        node.delete()
        assertEquals("age >= 1 and name ", node.toQuery())
        node.delete()
        assertEquals("age >= 1 and name", node.toQuery())
    }

    @Test
    fun deleteTest() {
        val node = ExpressionsGroupNode(listOf(
                int("age"),
                string("name")
        ))
        node.append('a')
        node.complete()
        node.append('=')
        node.complete()
        node.append('1')
        node.append('0')

        assertEquals("age = 10", node.toQuery())

        node.delete()

        assertEquals("age = 1", node.toQuery())

        node.delete()

        assertEquals("age = ", node.toQuery())

        node.delete()

        assertEquals("age =", node.toQuery())

        node.delete()

        assertEquals("age ", node.toQuery())

        node.delete()

        assertEquals("age", node.toQuery())

        node.delete()

        assertEquals("ag", node.toQuery())

        node.delete()

        assertEquals("a", node.toQuery())

        node.delete()

        assertEquals("", node.toQuery())
    }
}
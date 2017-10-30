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

        node.addChar('a')
        node.complete()
        assertEquals("age ", node.toQuery())
        node.addChar('>')
        node.addChar('=')
        node.complete()
        node.addChar('1')

        assertEquals("age >= 1", node.toQuery())

        node.complete()

        node.addChar('a')
        node.complete()

        assertEquals("age >= 1 and ", node.toQuery())

        node.addChar('n')
        node.addChar('a')
        node.addChar('m')
        node.addChar('e')

        node.complete()

        node.addChar('!')
        node.addChar('=')
        node.complete()

        node.addChar('z')
        node.addChar('z')
        node.addChar('z')

        assertEquals("age >= 1 and name != zzz", node.toQuery())

        node.deleteChar()

        assertEquals("age >= 1 and name != zz", node.toQuery())

        node.deleteChar()

        assertEquals("age >= 1 and name != z", node.toQuery())
        node.deleteChar()
        assertEquals("age >= 1 and name != ", node.toQuery())
        node.deleteChar()
        assertEquals("age >= 1 and name !=", node.toQuery())
        node.deleteChar()
        assertEquals("age >= 1 and name !", node.toQuery())
        node.deleteChar()
        assertEquals("age >= 1 and name ", node.toQuery())
        node.deleteChar()
        assertEquals("age >= 1 and name", node.toQuery())
    }

    @Test
    fun deleteTest() {
        val node = ExpressionsGroupNode(listOf(
                int("age"),
                string("name")
        ))
        node.addChar('a')
        node.complete()
        node.addChar('=')
        node.complete()
        node.addChar('1')
        node.addChar('0')

        assertEquals("age = 10", node.toQuery())

        node.deleteChar()

        assertEquals("age = 1", node.toQuery())

        node.deleteChar()

        assertEquals("age = ", node.toQuery())

        node.deleteChar()

        assertEquals("age =", node.toQuery())

        node.deleteChar()

        assertEquals("age ", node.toQuery())

        node.deleteChar()

        assertEquals("age", node.toQuery())

        node.deleteChar()

        assertEquals("ag", node.toQuery())

        node.deleteChar()

        assertEquals("a", node.toQuery())

        node.deleteChar()

        assertEquals("", node.toQuery())
    }
}
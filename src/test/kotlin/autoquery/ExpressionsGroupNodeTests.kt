package autoquery

import autoquery.nodes.ExpressionsGroupNode
import org.junit.Test
import kotlin.test.assertEquals

class ExpressionsGroupNodeTests {
    @Test
    fun test() {
        val node = ExpressionsGroupNode(listOf(
                int("age"),
                string("name")
        ))

        node.append('a')
        node.complete()
        assertEquals("age", node.toQuery())
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
    }
}
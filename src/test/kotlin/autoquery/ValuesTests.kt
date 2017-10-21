package autoquery

import autoquery.nodes.values.BooleanValueNode
import autoquery.nodes.values.FloatValueNode
import autoquery.nodes.values.StringValueNode
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ValuesTests {
    @Test
    fun testFloatInvalid() {
        val node = FloatValueNode()
        node.append('z')
        assertEquals("", node.toQuery())
    }

    @Test
    fun testFloatValid() {
        val node = FloatValueNode()
        node.append('0')
        node.append('0')
        node.append('.')
        node.append('9')
        node.append('5')
        assertTrue(node.complete())
        assertEquals("00.95", node.toQuery())
        assertTrue(node.isCompleted())
    }

    @Test
    fun testBoolean() {
        val node = BooleanValueNode()
        node.append('t')
        node.append('r')
        node.append('u')
        node.append('e')
        assertTrue(node.complete())
        assertEquals("true", node.toQuery())
        assertTrue(node.isCompleted())
    }

    @Test
    fun stringValueTests() {
        val node = StringValueNode()
        node.append('x')
        node.append('y')
        node.append('z')
        node.complete()
        assertTrue(node.isCompleted())
        assertEquals("xyz", node.toQuery())
    }

}
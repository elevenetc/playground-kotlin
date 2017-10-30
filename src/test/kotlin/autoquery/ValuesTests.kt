package autoquery

import autoquery.values.BooleanValueNode
import autoquery.values.FloatValueNode
import autoquery.values.StringValueNode
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ValuesTests {
    @Test
    fun testFloatInvalid() {
        val node = FloatValueNode()
        node.addChar('z')
        assertEquals("", node.toQuery())
    }

    @Test
    fun testFloatValid() {
        val node = FloatValueNode()
        node.addChar('0')
        node.addChar('0')
        node.addChar('.')
        node.addChar('9')
        node.addChar('5')
        assertTrue(node.complete())
        assertEquals("00.95", node.toQuery())
        assertTrue(node.isCompleted())
    }

    @Test
    fun testBoolean() {
        val node = BooleanValueNode()
        node.addChar('t')
        node.addChar('r')
        node.addChar('u')
        node.addChar('e')
        assertTrue(node.complete())
        assertEquals("true", node.toQuery())
        assertTrue(node.isCompleted())
    }

    @Test
    fun stringValueTests() {
        val node = StringValueNode()
        node.addChar('x')
        node.addChar('y')
        node.addChar('z')
        node.complete()
        assertTrue(node.isCompleted())
        assertEquals("xyz", node.toQuery())
    }

}
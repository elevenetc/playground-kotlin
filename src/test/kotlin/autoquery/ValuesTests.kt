package autoquery

import autoquery.nodes.values.FloatValueNode
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
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
        assertFalse(node.isCompleted())
    }

}
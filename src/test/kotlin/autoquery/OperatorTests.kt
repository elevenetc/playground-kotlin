package autoquery

import autoquery.nodes.operators.BooleanOperatorNode
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Test
import kotlin.test.assertEquals

class OperatorTests {
    @Test
    fun booleanEqual() {
        val operator = BooleanOperatorNode()
        operator.append('=')
        assertTrue(operator.complete())
        assertEquals("=", operator.toQuery())
    }

    @Test
    fun booleanNotEqual() {
        val operator = BooleanOperatorNode()
        operator.append('!')
        operator.append('=')
        assertTrue(operator.complete())
        assertEquals("!=", operator.toQuery())
    }

    @Test
    fun booleanInvalid() {
        val operator = BooleanOperatorNode()
        operator.append('Z')
        operator.append('=')
        assertFalse(operator.complete())
        assertEquals("Z=", operator.toQuery())
    }

}
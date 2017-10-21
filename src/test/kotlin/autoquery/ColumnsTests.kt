package autoquery

import autoquery.columns.BooleanColumn
import autoquery.columns.FloatColumn
import autoquery.columns.IntColumn
import autoquery.columns.StringColumn
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Test

class ColumnsTests {
    @Test
    fun testsBooleanColumn() {
        val column = BooleanColumn("name")
        assertTrue(column.isValidType("true"))
        assertTrue(column.isValidType("false"))
        assertFalse(column.isValidType(""))
    }

    @Test
    fun testsStringColumn() {
        val column = StringColumn("name")
        assertTrue(column.isValidType("true"))
        assertTrue(column.isValidType("false"))
        assertTrue(column.isValidType(""))
    }

    @Test
    fun testsIntColumn() {
        val column = IntColumn("name")
        assertTrue(column.isValidType("1"))
        assertTrue(column.isValidType("10"))
        assertFalse(column.isValidType("10.0"))
        assertFalse(column.isValidType("F"))
    }

    @Test
    fun testsFloatColumn() {
        val column = FloatColumn("name")
        assertTrue(column.isValidType("0.0"))
        assertTrue(column.isValidType("1.0"))
        assertTrue(column.isValidType("10.555"))
        assertFalse(column.isValidType("F"))
        assertFalse(column.isValidType("F"))
    }

}
package autoquery

import autoquery.*
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SelectQueryTests {
    @Test
    fun hello() {

        val query: Query = SelectQuery(
                Table("students", int("age"), string("name")),
                Table("teachers", string("subject"))
        )

        assertEquals(0, query.currentIndex())

        query.append('s')
        query.complete()

        assertEquals("select", query.getCurrent()[0].simpleName())
        assertEquals(1, query.currentIndex())

        query.append('n')
        query.append('a')
        query.complete()

        assertEquals(2, query.currentIndex())
        assertEquals("select", query.getCurrent()[0].simpleName())
        assertEquals("name", query.getCurrent()[1].simpleName())
        assertEquals("select, name", query.getSimpleName())
    }
}
package utils

import autoquery.*
import org.junit.Test
import kotlin.test.assertEquals

class AutoTreeTests {
    @Test
    fun hello() {

        val query: Query = SelectQuery(
                Table("students", int("age"), string("name")),
                Table("teachers", string("subject"))
        )

        query.append('s')
        query.complete()
        assertEquals(1, query.getCurrent().size)
        assertEquals("select", query.getCurrent()[0].simpleName())
    }
}
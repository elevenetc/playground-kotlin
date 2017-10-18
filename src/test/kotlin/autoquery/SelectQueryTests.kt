package autoquery

import org.junit.Test
import kotlin.test.assertEquals

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

        query.append('f')
        query.complete()

        assertEquals(3, query.currentIndex())
        assertEquals("select", query.getCurrent()[0].simpleName())
        assertEquals("name", query.getCurrent()[1].simpleName())
        assertEquals("from", query.getCurrent()[2].simpleName())
        assertEquals("select, name, from", query.getSimpleName())

        query.append('s')
        query.append('t')
        query.complete()

        assertEquals(4, query.currentIndex())
        assertEquals("select", query.getCurrent()[0].simpleName())
        assertEquals("name", query.getCurrent()[1].simpleName())
        assertEquals("from", query.getCurrent()[2].simpleName())
        assertEquals("students", query.getCurrent()[3].simpleName())
        assertEquals("select, name, from, students", query.getSimpleName())

        query.append('w')
        query.complete()

        assertEquals(5, query.currentIndex())
        assertEquals("select", query.getCurrent()[0].simpleName())
        assertEquals("name", query.getCurrent()[1].simpleName())
        assertEquals("from", query.getCurrent()[2].simpleName())
        assertEquals("students", query.getCurrent()[3].simpleName())
        assertEquals("where", query.getCurrent()[4].simpleName())
        assertEquals("select, name, from, students, where", query.getSimpleName())
    }
}
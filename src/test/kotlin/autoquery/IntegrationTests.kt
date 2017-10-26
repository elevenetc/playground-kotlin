package autoquery

import org.junit.Test
import kotlin.test.assertEquals

class IntegrationTests {
    @Test
    fun selectFillAndDelete() {

        val query: Query = SelectQuery(
                Table("students", int("age"), string("name")),
                Table("teachers", string("subject"))
        )

        query.append('s')
        query.complete()
        query.append('n')
        query.append('a')
        query.complete()

        assertEquals("select name", query.toQuery())

        query.append('f')
        query.complete()

        assertEquals("select name from", query.toQuery())

        query.append('s')
        query.append('t')
        query.complete()

        assertEquals("select name from students", query.toQuery())

        query.append('w')
        query.complete()

        assertEquals("select name from students where", query.toQuery())

        query.append('a')
        query.append('g')
        query.append('e')
        query.complete()

        assertEquals("select name from students where age", query.toQuery())

        query.append('<')
        query.append('=')
        query.complete()
        query.append('1')
        query.append('8')
        query.complete()

        assertEquals("select name from students where age <= 18 ", query.toQuery())

        query.append('o')
        query.append('r')
        query.complete()

        assertEquals("select name from students where age <= 18 or ", query.toQuery())

        query.append('n')
        query.complete()
        query.append('=')
        query.complete()
        query.append('b')
        query.append('o')
        query.append('b')

        val fullQuery = "select name from students where age <= 18 or name = bob"
        assertEquals(fullQuery, query.toQuery())

        val sb = StringBuilder(fullQuery)
        while (!sb.isEmpty()) {
            query.deleteChar()
            sb.setLength(sb.length - 1)

            assertEquals(sb.toString(), query.toQuery())
        }
    }
}
package autoquery

import org.junit.Test
import kotlin.test.assertEquals

class IntegrationTests {
    @Test
    fun oneColumn() {

        val query: Query = SelectQuery(
                Table("students", int("age"), string("name")),
                Table("teachers", string("subject"))
        )

        query.addChar('s')
        query.complete()
        query.addChar('n')
        query.addChar('a')
        query.complete()

        assertEquals("select name", query.toQuery())

        query.complete()
        query.complete()//finish columns selection

        query.addChar('f')
        query.complete()

        assertEquals("select name from ", query.toQuery())

        query.addChar('s')
        query.addChar('t')
        query.complete()

        assertEquals("select name from students ", query.toQuery())

        query.addChar('w')
        query.complete()

        assertEquals("select name from students where ", query.toQuery())

        query.addChar('a')
        query.addChar('g')
        query.addChar('e')
        query.complete()

        assertEquals("select name from students where age ", query.toQuery())

        query.addChar('<')
        query.addChar('=')
        query.complete()
        query.addChar('1')
        query.addChar('8')
        query.complete()

        assertEquals("select name from students where age <= 18 ", query.toQuery())

        query.addChar('o')
        query.addChar('r')
        query.complete()

        assertEquals("select name from students where age <= 18 or ", query.toQuery())

        query.addChar('n')
        query.complete()
        query.addChar('=')
        query.complete()
        query.addChar('b')
        query.addChar('o')
        query.addChar('b')

        val fullQuery = "select name from students where age <= 18 or name = bob"
        assertEquals(fullQuery, query.toQuery())

        deleteAll(fullQuery, query)
    }

    @Test
    fun twoColumns() {

        val query: Query = SelectQuery(
                Table("students", int("age"), string("name")),
                Table("teachers", string("subject"))
        )

        query.addChar('s')
        query.complete()
        query.addChar('n')
        query.addChar('a')
        query.complete()

        assertEquals("select name", query.toQuery())

        query.addChar(',')
        query.complete()


        query.addChar('a')
        query.complete()

        assertEquals("select name, age", query.toQuery())

        query.complete()
        query.complete()

        query.addChar('f')
        query.addChar('r')

        query.complete()

        assertEquals("select name, age from ", query.toQuery())

        query.addChar('s')
        query.addChar('t')

        query.complete()

        assertEquals("select name, age from students ", query.toQuery())

        query.addChar('w')
        query.complete()

        assertEquals("select name, age from students where ", query.toQuery())

        query.addChar('a')
        query.addChar('g')
        query.addChar('e')
        query.complete()

        assertEquals("select name, age from students where age ", query.toQuery())

        query.addChar('<')
        query.addChar('=')
        query.complete()
        query.addChar('1')
        query.addChar('8')
        query.complete()

        assertEquals("select name, age from students where age <= 18 ", query.toQuery())

        query.addChar('o')
        query.addChar('r')
        query.complete()

        assertEquals("select name, age from students where age <= 18 or ", query.toQuery())

        query.addChar('n')
        query.complete()
        query.addChar('=')
        query.complete()
        query.addChar('b')
        query.addChar('o')
        query.addChar('b')

        val fullQuery = "select name, age from students where age <= 18 or name = bob"
        assertEquals(fullQuery, query.toQuery())

        deleteAll(fullQuery, query)
    }


    private fun deleteAll(fullQuery: String, query: Query) {
        val sb = StringBuilder(fullQuery)

        while (!sb.isEmpty()) {
            query.deleteChar()
            sb.setLength(sb.length - 1)

            val expected = sb.toString()
            val current = query.toQuery()
            println(sb.length.toString() + "expected:'" + expected + "' <> actual:'" + current + "'")
            assertEquals(expected, current)
        }
    }
}
package utils

import autoquery.Query
import autoquery.select
import org.junit.Test

class AutoTreeTests {
    @Test
    fun hello() {
        val root =
                select(
                        table("students").withColumns("id", "grade"),
                        table("teachers").withColumns("id", "subject")
                ).where()
                        .selectColumns()
                        .build()
    }
}

fun initFrom(message: String): Query {
    return Query(message)
}

fun table(name: String): Query {
    return Query(name)
}
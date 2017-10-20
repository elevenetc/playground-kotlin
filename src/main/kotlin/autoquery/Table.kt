package autoquery

import autoquery.columns.IntColumn
import autoquery.columns.StringColumn

class Table(val name: String, vararg val columns: Column<*>)

fun int(name: String): Column<Int> {
    return IntColumn(name)
}

fun string(name: String): Column<String> {
    return StringColumn(name)
}


package autoquery

class Table(val name: String, vararg val columns: Column<*>)

fun int(name: String): Column<Int> {
    return IntColumn(name)
}

fun string(name: String): Column<String> {
    return StringColumn(name)
}


package autoquery

class Table(val name: String, vararg columns: Column<*>) {
    val columns = columns
}

fun int(name: String): Column<Int> {
    return IntColumn(name)
}

fun string(name: String): Column<String> {
    return StringColumn(name)
}


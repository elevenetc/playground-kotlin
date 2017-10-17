package autoquery

class Table(val name: String, vararg columns: Column) {
    val columns = columns
}

fun int(name: String): Column {
    return Column(name, Int.javaClass)
}

fun string(name: String): Column {
    return Column(name, String.javaClass)
}


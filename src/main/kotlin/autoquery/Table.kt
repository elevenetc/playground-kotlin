package autoquery

class Table(name: String, vararg columns: Column) {

}

fun int(name: String): Column {
    return Column(name, Int.javaClass)
}

fun string(name: String): Column {
    return Column(name, String.javaClass)
}


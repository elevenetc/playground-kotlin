package autoquery

abstract class Node {

    var isCompleted: Boolean = false
    var value: StringBuilder = StringBuilder()

    abstract fun append(char: Char): Boolean
    abstract fun complete(): Boolean

    open fun toQuery(): String {
        return value.toString()
    }
}
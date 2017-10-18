package autoquery

abstract class Node() {

    var isCompleted: Boolean = false
    var value: StringBuilder = StringBuilder()

    abstract fun append(char: Char)
    abstract fun complete(): Boolean

    open fun simpleName(): String {
        return value.toString()
    }
}
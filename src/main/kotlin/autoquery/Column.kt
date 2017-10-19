package autoquery


abstract open class Column<T>(
        val name: String,
        private val type: Class<T>,
        var value: T
) {

    private val stringValue = StringBuilder()
    val defaultValue: T = value

    abstract fun isValidType(value: String): Boolean

    fun stringValue(): String {
        return stringValue.toString()
    }

    fun append(char: Char) {
        stringValue.append(char)
    }
}
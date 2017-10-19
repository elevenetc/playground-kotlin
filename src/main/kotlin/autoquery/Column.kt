package autoquery


abstract open class Column<T>(
        val name: String,
        private val type: Class<T>,
        var value: T
) {

    val defaultValue: T = value

    abstract fun setValue(newValue: String)
    abstract fun isValidType(value: String): Boolean
}
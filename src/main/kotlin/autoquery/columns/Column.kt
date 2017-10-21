package autoquery.columns


abstract open class Column<T>(
        val name: String,
        private val type: Class<T>,
        var value: T
) {

    private val stringValue = StringBuilder()
    val defaultValue: T = value

    abstract fun isValidType(value: String): Boolean
}
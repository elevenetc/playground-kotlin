package autoquery


abstract open class Column<T>(
        val name: String,
        private val type: Class<T>,
        var value: T,
        private var stringValue: String = ""
) {

    val defaultValue: T = value

    abstract fun isValidType(value: String): Boolean

    fun setValue(newValue: String) {
        stringValue = newValue
    }

    fun stringValue(): String {
        return stringValue
    }
}
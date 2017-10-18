package autoquery

class Column(val name: String, private val type: Class<Any>) {
    fun isValidType(value: String): Boolean {
        if (value.isEmpty()) return false
        return when (type) {
            Int::class -> try {
                Integer.parseInt(value)
                true
            } catch (e: NumberFormatException) {
                false
            }
            String::class -> true
            else -> throw IllegalArgumentException("Unsupported column type: $type")
        }
    }
}
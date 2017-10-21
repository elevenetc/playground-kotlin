package autoquery.columns

class FloatColumn(name: String) : Column<Float>(name, Float::class.java, 0.0f) {

    override fun isValidType(value: String): Boolean {
        return try {
            value.toFloat()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

}
package autoquery

class IntColumn(name: String) : Column<Int>(name, Int::class.java, 0) {

    override fun setValue(newValue: String) {
        value = Integer.parseInt(newValue)
    }

    override fun isValidType(value: String): Boolean {
        return try {
            Integer.parseInt(value)
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

}
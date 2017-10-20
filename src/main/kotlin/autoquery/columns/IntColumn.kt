package autoquery.columns

import autoquery.Column

class IntColumn(name: String) : Column<Int>(name, Int::class.java, 0) {

    override fun isValidType(value: String): Boolean {
        return try {
            value.toInt()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

}
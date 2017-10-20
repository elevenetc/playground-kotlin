package autoquery.columns

import autoquery.Column

class BooleanColumn(name: String) : Column<Boolean>(name, Boolean::class.java, false) {

    override fun isValidType(value: String): Boolean {
        return value == "true" || value == "false"
    }

}
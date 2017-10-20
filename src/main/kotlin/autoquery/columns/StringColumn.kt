package autoquery.columns

import autoquery.Column

class StringColumn(name: String) : Column<String>(name, String::class.java, "") {

    override fun isValidType(value: String): Boolean {
        return true
    }

}
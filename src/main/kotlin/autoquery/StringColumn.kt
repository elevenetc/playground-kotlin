package autoquery

class StringColumn(name: String) : Column<String>(name, String::class.java, "") {

    override fun setValue(value: String) {
        this.value = value
    }

    override fun isValidType(value: String): Boolean {
        return true
    }

}
package autoquery

class ExpressionsNode(private val columnsVariants: List<Column<*>>) : Node() {

    private val addedColumns = mutableListOf<Column<*>>()
    private var mode = Mode.NAME
    private var newColumnName = StringBuilder()

    override fun append(char: Char) {
        //if (isCompleted) return last editable > never completed

        if (mode == Mode.NAME) {
            newColumnName.append(char)
            isFitSomeName()
        } else if (mode == Mode.VALUE) {

            val column = addedColumns.last()
            val currentValue = column.stringValue()
            if (char == ',') {
                mode = Mode.NAME
            } else {
                var testValue = currentValue
                testValue += char
                if (column.isValidType(testValue)) {
                    column.append(char)
                } else {
                    //value doesn't fit type
                }
            }
        }
    }

    override fun complete(): Boolean {

        if (mode == Mode.NAME) {

            tryToComplete()
            return mode == Mode.VALUE

        } else if (mode == Mode.VALUE) {
            return false
        } else {
            throw IllegalArgumentException("Unsupported mode")
        }
    }

    override fun simpleName(): String {
        val result = StringBuilder()
        for (i in 0 until addedColumns.size) {
            val column = addedColumns[i]

            result.append(column.name)
            result.append(" = ")
            result.append(column.stringValue())

            if (i != addedColumns.size - 1) {
                result.append(", ")
            }
        }

        if (newColumnName.isNotEmpty()) {
            if (result.isEmpty()) {
                result.append(newColumnName.toString())
            } else {
                result.append(", ")
                result.append(newColumnName.toString())
            }
        }

        return result.toString()
    }

    private fun isFitSomeName() {
        val index = getFullCompletableIndex(
                newColumnName,
                columnsVariants.map { it.name },
                addedColumns.map { it.name }
        )
        if (index != -1) {
            addNewColumn(columnsVariants[index])
        }
    }

    private fun tryToComplete() {

        val shortest = getShortestCompletable(
                newColumnName,
                columnsVariants.map { it.name },
                addedColumns.map { it.name }
        )

        if (shortest.isNotEmpty()) {
            addNewColumn(columnsVariants.first { it.name == shortest })
        }
    }

    private fun addNewColumn(column: Column<*>) {
        addedColumns.add(column)
        newColumnName.setLength(0)
        mode = Mode.VALUE
        isCompleted = addedColumns.size == columnsVariants.size
    }

    enum class Mode {
        NAME, VALUE
    }
}


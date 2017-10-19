package autoquery

class ExpressionsNode(private val columnsVariants: List<Column<*>>) : Node() {

    private val addedColumns = mutableListOf<Column<*>>()
    private var mode = Mode.NAME

    override fun append(char: Char) {
        //if (isCompleted) return last editable > never completed

        if (mode == Mode.NAME) {
            value.append(char)
            isFitSomeName()
        } else if (mode == Mode.VALUE) {

            val column = addedColumns.last()
            val currentValue = value.toString()
            if (char == ',') {

                if (column.isValidType(currentValue)) {
                    column.setValue(currentValue)
                    value.setLength(0)
                    mode = Mode.NAME
                } else {
                    //value doesn't fit type
                }

            } else {
                var testValue = currentValue
                testValue += char
                if (column.isValidType(testValue)) {
                    value.append(char)
                    column.setValue(value.toString())
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
        return result.toString()
    }

    private fun isFitSomeName() {
        val index = getFullCompletableIndex(
                value,
                columnsVariants.map { it.name },
                addedColumns.map { it.name }
        )
        if (index != -1) {
            addNewColumn(columnsVariants[index])
        }
    }

    private fun tryToComplete() {

        val shortest = getShortestCompletable(
                value,
                columnsVariants.map { it.name },
                addedColumns.map { it.name }
        )

        if (shortest.isNotEmpty()) {
            addNewColumn(columnsVariants.first { it.name == shortest })
        }
    }

    private fun addNewColumn(column: Column<*>) {
        addedColumns.add(column)
        value.setLength(0)
        mode = Mode.VALUE
        isCompleted = addedColumns.size == columnsVariants.size
    }


    enum class Mode {
        NAME, VALUE
    }
}


package autoquery

class ExpressionsNode(private val columnsVariants: List<Column<*>>) : Node() {

    val addedColumns = mutableListOf<Column<*>>()
    private var mode = Mode.NAME
    var newColumnName = StringBuilder()
    var union = StringBuilder()

    override fun append(char: Char): Boolean {
        //if (isCompleted) return last editable > never completed

        if (mode == Mode.NAME) {
            newColumnName.append(char)
            isFitSomeName()
            return true
        } else if (mode == Mode.VALUE) {

            val column = addedColumns.last()
            val currentValue = column.stringValue()
            if (char == ',' && !isInLastColumn()) {
                mode = Mode.UNION
                return true
            } else {
                var testValue = currentValue
                testValue += char
                if (column.isValidType(testValue)) {
                    column.append(char)
                    return true
                } else {
                    //value doesn't fit type
                    return false
                }
            }
        } else if (mode == Mode.UNION) {
            var currentUnion = union.toString()
            currentUnion += char

            if (currentUnion == "and") {
                setUnion(Union.AND)
                //TODO: what of last col?
            } else if (currentUnion == "or") {
                setUnion(Union.OR)
                //TODO: what of last col?
            } else {
                union.append(char)
            }
            return true
        }

        return false

    }

    private fun setUnion(u: Union) {
        addedColumns.last().union = u
        union.setLength(0)
        mode = Mode.NAME
    }

    override fun complete(): Boolean {

        if (mode == Mode.NAME) {

            tryToCompleteColumn()
            return mode == Mode.VALUE

        } else if (mode == Mode.VALUE) {
            return false
        } else if (mode == Mode.UNION) {
            tryToCompleteUnion()
            return mode == Mode.NAME
        } else {
            throw IllegalArgumentException("Unsupported mode")
        }
    }

    override fun toQuery(): String {
        return toQuery(this)
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

    private fun tryToCompleteColumn() {

        val shortest = getShortestCompletable(
                newColumnName,
                columnsVariants.map { it.name },
                addedColumns.map { it.name }
        )

        if (shortest.isNotEmpty()) {
            addNewColumn(columnsVariants.first { it.name == shortest })
        }
    }

    private fun tryToCompleteUnion() {
        val shortest = getShortestCompletable(union, listOf("and", "or"))

        if (shortest.isNotEmpty()) {
            if (shortest == "or") {
                setUnion(Union.OR)
            } else {
                setUnion(Union.AND)
            }
        }
    }

    private fun addNewColumn(column: Column<*>) {
        addedColumns.add(column)
        newColumnName.setLength(0)
        mode = Mode.VALUE
        isCompleted = addedColumns.size == columnsVariants.size
    }

    private fun isInLastColumn(): Boolean {
        return addedColumns.size == columnsVariants.size
    }

    enum class Mode {
        NAME, VALUE, UNION
    }
}


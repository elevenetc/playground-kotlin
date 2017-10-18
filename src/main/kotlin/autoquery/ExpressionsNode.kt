package autoquery

class ExpressionsNode(private val columns: List<Column>) : Node() {

    private val addedColumns = mutableListOf<Column>()
    private var mode: Mode = Mode.NAME

    override fun append(char: Char) {
        if (isCompleted) return

        if (mode == Mode.NAME) {
            value.append(char)
            tryToComplete()
        } else if (mode == Mode.VALUE) {
            val column = addedColumns.last()
            var testValue = value.toString()
            testValue += char
            if (column.isValidType(testValue)) {
                value.append(char)
            } else {
                //invalid type
            }
        }
    }

    private fun tryToComplete() {

        val shortest = getShortestCompletable(
                value,
                columns.map { it.name },
                addedColumns.map { it.name }
        )

        if (shortest.isNotEmpty()) {
            val column: Column = columns.first { it.name == shortest }
            addedColumns.add(column)
            value.setLength(0)
            mode = Mode.VALUE
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
        return
    }
}

private enum class Mode {
    NAME, VALUE
}
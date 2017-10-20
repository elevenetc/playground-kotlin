package autoquery

import java.util.*

class SelectQuery(private vararg val tables: Table) : Query {

    override fun currentIndex(): Int {
        return currentIndex
    }

    private val queue: MutableList<Node> = LinkedList()
    private var currentIndex: Int = 0

    init {
        val columnsNames = getColumnsNames()
        val tableNames = getTablesNames()
        val columns = getColumns()
        queue.add(SingleNode("select"))
        queue.add(AndNode(columnsNames))
        queue.add(SingleNode("from"))
        queue.add(OrNode(tableNames))
        queue.add(SingleNode("where"))
        queue.add(ExpressionsNode(columns))
    }

    override fun append(char: Char) {
        val node = queue[currentIndex]
        node.append(char)
        if (node.isCompleted) {
            moveToNext()
        }
    }

    override fun complete() {
        val node = queue[currentIndex]
        if (node.complete()) {
            moveToNext()
        }
    }

    override fun getCurrent(): List<Node> {
        return queue
    }

    override fun toQuery(): String {
        return toSimpleString(queue)
    }

    private fun getColumns(): List<Column<*>> {
        val result = mutableListOf<Column<*>>()
        for (table in tables) result += table.columns
        return result
    }

    private fun getColumnsNames(): List<String> {
        val result = mutableListOf<String>()
        for (table in tables)
            table.columns.mapTo(result) { it.name }
        return result
    }

    private fun getTablesNames(): List<String> {
        return tables.map { it.name }
    }

    private fun current(): Node {
        return queue[currentIndex]
    }

    private fun moveToNext() {
        if (currentIndex < queue.size - 1) {
            currentIndex++
        }
    }


}
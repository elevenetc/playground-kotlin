package autoquery

import java.util.*

class SelectQuery(vararg tables: Table) : Query {

    override fun currentIndex(): Int {
        return currentIndex
    }

    private val queue: MutableList<Node> = LinkedList()
    private val tables = tables
    private var currentIndex: Int = 0

    init {
        queue.add(SingleNode("select"))
        queue.add(AndNode(getColumns()))
        queue.add(SingleNode("from"))
        queue.add(OrNode(getTablesNames()))
        queue.add(SingleNode("where"))
    }

    fun next() {
        if (!current().isCompleted) current().complete()
        if (current().isCompleted) moveToNext()
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

    private fun getColumns(): List<String> {
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
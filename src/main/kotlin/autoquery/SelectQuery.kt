package autoquery

import autoquery.columns.Column
import autoquery.nodes.*
import java.util.*

class SelectQuery(private vararg val tables: Table) : Query {

    override fun currentIndex(): Int {
        return currentIndex
    }

    private val nodes: MutableList<Node> = LinkedList()
    private var currentIndex: Int = 0

    init {
        val columnsNames = getColumnsNames()
        val tableNames = getTablesNames()
        val columns = getColumns()
        nodes.add(SingleNode("select"))
        nodes.add(FixedSpaceNode())
        nodes.add(ColumnsNode(columnsNames))
        nodes.add(FixedSpaceNode())
        nodes.add(SingleNode("from"))
        nodes.add(FixedSpaceNode())
        nodes.add(OrNode(tableNames))
        nodes.add(FixedSpaceNode())
        nodes.add(SingleNode("where"))
        nodes.add(FixedSpaceNode())
        nodes.add(ExpressionsGroupNode(columns))
    }

    override fun append(char: Char) {
        val node = nodes[currentIndex]
        node.addChar(char)
        //if (node.isCompleted()) {
        //    moveToNext()
        //}
    }

    override fun complete() {
        val node = nodes[currentIndex]
        if (node.complete()) {
            moveToNext()
        }
    }

    override fun getCurrent(): List<Node> {
        return nodes
    }

    override fun toQuery(): String {
        return selectToQuery(nodes, currentIndex)
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

    override fun deleteChar(): Boolean {

        if (nodes[currentIndex] is FixedSpaceNode) {
            currentIndex--
            return true
        }

        return if (currentIndex == 0 && nodes[0].isEmpty()) {
            false
        } else if (nodes[currentIndex].isEmpty()) {
            currentIndex--
            deleteChar()
        } else {
            nodes[currentIndex].deleteChar()
        }
    }

    private fun moveToNext() {
        if (currentIndex < nodes.size - 1) {
            currentIndex++

            if (nodes[currentIndex] is FixedSpaceNode) moveToNext()
        }
    }


}
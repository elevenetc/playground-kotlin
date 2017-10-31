package autoquery

import autoquery.columns.Column
import autoquery.nodes.*
import java.util.*

class SelectQuery(private vararg val tables: Table) : Query {

    private val nodes: MutableList<Node> = LinkedList()
    private var cursor: Int = 0
    private var lastAction = Action.EMPTY

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

    override fun cursorPosition(): Int {
        return cursor
    }


    override fun addChar(char: Char) {

        lastAction = Action.APPEND_CHAR

        val node = nodes[cursor]
        node.addChar(char)


        //if (node.isCompleted()) {
        //    moveToNext()
        //}
    }

    override fun complete(): Boolean {
        var result = false
        val node = nodes[cursor]

        if (node is ColumnsNode) {

            result = if (lastAction == Action.COMPLETE) {
                if (node.isValid()) {
                    moveToNext()
                    true
                } else {
                    node.complete()
                }
            } else {
                node.complete()
            }

        } else {
            if (node.complete()) {
                moveToNext()
                result = true
            }
        }

        lastAction = Action.COMPLETE
        return result
    }

    override fun getNodes(): List<Node> {
        return nodes
    }

    override fun toQuery(): String {
        return selectToQuery(nodes, cursor)
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

        lastAction = Action.DELETE_CHAR

        val node = nodes[cursor]

        if (node is FixedSpaceNode) {
            cursor--
            return true
        }

        return if (cursor == 0 && nodes[0].isEmpty()) {
            false
        } else if (node.isEmpty()) {
            cursor--
            deleteChar()
        } else {
            node.deleteChar()
        }
    }

    private fun moveToNext() {
        if (cursor < nodes.size - 1) {
            cursor++

            if (nodes[cursor] is FixedSpaceNode) moveToNext()
        }
    }

    enum class Action {
        EMPTY, APPEND_CHAR, DELETE_CHAR, COMPLETE
    }

}
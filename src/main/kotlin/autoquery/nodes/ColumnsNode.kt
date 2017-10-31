package autoquery.nodes

import autoquery.getShortestCompletable
import java.util.*

class ColumnsNode(private val columns: List<String>) : Node() {

    private val nodes = LinkedList<Node>()

    init {
        addSearchColumnNameNode()
    }

    fun isValid(): Boolean {
        return nodes.last is SingleNode
    }

    private fun addSearchColumnNameNode(value: String = "") {

        val cutNodes = mutableListOf<String>()

        for (column in columns) {
            val added = nodes.any { it.toQuery() == column }
            if (!added) cutNodes.add(column)
        }

        nodes.add(SearchColumnNameNode(cutNodes))

        if (!value.isEmpty()) {
            nodes.last.value.append(value)
        }
    }

    /**
     * Trying to complete [value].
     * If it's completed then new value is added to [completedValues]
     *
     * @return true when all [columns] are filled in [completedValues]
     */
    override fun complete(): Boolean {
        if (isCompleted()) return true

        if (nodes.last is CommaNode) {
            //"xxx,"
            addSearchColumnNameNode()
            return true
        } else {
            if (nodes.last.isCompleted()) {
                //"xxx" - already completed
                return false
            } else {
                //"xxx,a"
                val completedValues = mutableListOf<String>()
                val lastValue = nodes.last.toQuery()
                nodes.filter { it !is CommaNode }.mapTo(completedValues) { it.toQuery() }

                val shortest = getShortestCompletable(lastValue, columns, completedValues)
                return if (shortest.isEmpty()) {
                    //"xxx,a"
                    false
                } else {
                    //"xxx,aaa"
                    nodes.last.setCompleted(shortest)
                    completeCurrent()
                    true
                }
            }
        }
    }

    override fun addChar(char: Char): Boolean {
        if (isCompleted()) return false

        return if (char == ',') {

            val last = nodes.last()

            return if (last.isCompleted()) {
                nodes.add(CommaNode())
                addSearchColumnNameNode()
                true
            } else {
                last.complete()
            }
        } else {
            return nodes.last().addChar(char)
        }
    }

    override fun deleteChar(): Boolean {
        setNotCompleted()
        val last = nodes.last
        if (last is CommaNode) {
            nodes.removeLast()
            return true
        } else if (last is SingleNode) {

            last.deleteChar()
            val lastColumn = last.toQuery()
            nodes.removeLast()
            addSearchColumnNameNode(lastColumn)

            return true
        } else {
            //is SearchColumnNameNode
            if (!last.isEmpty()) {
                return last.deleteChar()
            } else {
                if (nodes.size == 1) {
                    return false
                } else {
                    nodes.removeLast()
                    return true
                }
            }
        }
    }

    private fun completeCurrent() {

        val last = nodes.last()

        nodes.removeLast()
        nodes.add(SingleNode(last.toQuery(), true))

        checkCompleted()
    }

    private fun checkCompleted() {
        if (nodes.last.isCompleted() && nodes.size - 1 == columns.size) {
            setCompleted()
        }
    }

    fun localIsCompleted(): Boolean {
        return nodes.last.isCompleted() && nodes.size - 1 == columns.size
    }

    override fun toQuery(): String {
        val sb = StringBuilder()
        for (node in nodes) {
            sb.append(node.toQuery())
        }
        return sb.toString()
    }

    override fun isEmpty(): Boolean {
        return nodes.size == 1 && nodes.last.isEmpty()
    }

    inner class SearchColumnNameNode(columnNames: List<String>) : OrNode(columnNames)
    inner class CommaNode : SingleNode(", ", true)
}
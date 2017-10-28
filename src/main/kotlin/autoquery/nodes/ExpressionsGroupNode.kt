package autoquery.nodes

import autoquery.columns.*
import autoquery.operators.BooleanOperatorNode
import autoquery.operators.OperatorNode
import autoquery.operators.StringOperatorNode
import autoquery.values.BooleanValueNode
import autoquery.values.FloatValueNode
import autoquery.values.IntValueNode
import autoquery.values.StringValueNode
import java.util.*

class ExpressionsGroupNode(private val columnsVariants: List<Column<*>>) : ExpressionsGroup, Node() {

    override fun addUnion() {
        val union = UnionNode()
        union.setOnCompletedHandler {
            addColumnNameNode()
        }
        nodes.add(union)
    }

    override fun removeLast(columnName: String) {
        selectedColumns.remove(columnName)
        nodes.removeLast()
        addColumnNameNode(columnName)
    }

    val nodes = LinkedList<Node>()
    private val selectedColumns = mutableListOf<String>()

    init {
        initGroup()
    }

    private fun addColumnNameNode(value: String = "") {

        if (isCompleted()) return

        columnsVariants.map { it.name }.filter { it in selectedColumns }

        val notSelectedColumns = columnsVariants
                .filterNot { selectedColumns.contains(it.name) }
                .map { it.name }

        val newNode = ColumnNameNode(
                notSelectedColumns
        ).setOnCompletedHandler({
            val selectedColumnName = it.value.toString()
            val selectedColumn: Column<*> = columnsVariants.first { it.name == selectedColumnName }
            nodes.removeLast()
            selectedColumns.add(selectedColumnName)

            when (selectedColumn) {
                is StringColumn -> nodes.add(ExpressionNode(this, selectedColumn, StringOperatorNode(), StringValueNode()))
                is BooleanColumn -> nodes.add(ExpressionNode(this, selectedColumn, BooleanOperatorNode(), BooleanValueNode()))
                is IntColumn -> nodes.add(ExpressionNode(this, selectedColumn, OperatorNode(), IntValueNode()))
                is FloatColumn -> nodes.add(ExpressionNode(this, selectedColumn, OperatorNode(), FloatValueNode()))
                else -> throw RuntimeException("Invalid column type: $selectedColumn")
            }
        })

        if (!value.isEmpty()) newNode.value.append(value)

        nodes.add(newNode)
    }

    override fun isCompleted(): Boolean {
        return selectedColumns.size == columnsVariants.size
    }

    override fun append(char: Char): Boolean {
        //if (isCompleted()) return true
        return nodes.last.append(char)
    }

    override fun complete(): Boolean {
        //if (isCompleted()) return true TODO: add complete case as it's needed to move to the next node
        return nodes.last.complete()
    }

    override fun toQuery(): String {
        val result = StringBuilder()
        for (i in 0 until nodes.size) {
            val node = nodes[i]
            result.append(node.toQuery())
            if (i != nodes.size - 1) {
                result.append(' ')
            }
        }
        return result.toString()
    }

    /**
     * Removes last char from last node of [nodes]
     * If node isEmpty then it removed from [nodes]
     *
     * So empty node is acceptable because user should have ability to start typing again
     */
    override fun delete(): Boolean {
        return if (nodes[0].isEmpty()) {
            onDeletedAll(this)
            false
        } else {
            if (isCompleted()) setNotCompleted()
            val lastNode = nodes.last
            return if (lastNode.isEmpty()) {
                nodes.removeLast()
                if (nodes.isEmpty()) initGroup()
                true
            } else {
                lastNode.delete()
            }
        }
    }

    override fun isEmpty(): Boolean {
        return nodes.size == 1 && nodes.last.isEmpty()
    }

    private fun initGroup() {
        addColumnNameNode()
    }

    inner class ColumnNameNode(columnNames: List<String>) : OrNode(columnNames)

}
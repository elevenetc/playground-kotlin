package autoquery

import autoquery.columns.BooleanColumn
import autoquery.columns.FloatColumn
import autoquery.columns.IntColumn
import autoquery.columns.StringColumn
import autoquery.nodes.operators.BooleanOperatorNode
import autoquery.nodes.operators.OperatorNode
import autoquery.nodes.operators.StringOperatorNode
import autoquery.nodes.values.BooleanValueNode
import autoquery.nodes.values.FloatValueNode
import autoquery.nodes.values.IntValueNode
import autoquery.nodes.values.StringValueNode
import java.util.*

class ExpressionsGroupNode(private val columnsVariants: List<Column<*>>) : Node() {

    private val nodes = LinkedList<Node>()
    private val selectedColumns = mutableListOf<String>()

    init {
        addColumnNameNode()
    }

    private fun addColumnNameNode() {

        if (isCompleted()) return

        columnsVariants.map { it.name }.filter { it in selectedColumns }

        val notSelectedColumns = columnsVariants
                .filterNot { selectedColumns.contains(it.name) }
                .map { it.name }

        nodes.add(ColumnNameNode(
                notSelectedColumns
        ).setOnCompletedHandler({
            val selectedColumnName = it.value.toString()
            val selectedColumn: Column<*> = columnsVariants.first { it.name == selectedColumnName }
            nodes.removeLast()
            selectedColumns.add(selectedColumnName)

            if (selectedColumn is StringColumn) {
                nodes.add(ExpressionNode(selectedColumn, StringOperatorNode(), StringValueNode()))
            } else if (selectedColumn is BooleanColumn) {
                nodes.add(ExpressionNode(selectedColumn, BooleanOperatorNode(), BooleanValueNode()))
            } else if (selectedColumn is IntColumn) {
                nodes.add(ExpressionNode(selectedColumn, OperatorNode(), IntValueNode()))
            } else if (selectedColumn is FloatColumn) {
                nodes.add(ExpressionNode(selectedColumn, OperatorNode(), FloatValueNode()))
            } else {
                throw RuntimeException("Invalid column type: $selectedColumn")
            }
        }))
    }

    override fun isCompleted(): Boolean {
        return selectedColumns.size == columnsVariants.size
    }

    override fun append(char: Char): Boolean {
        if (isCompleted()) return true
        return nodes.last.append(char)
    }

    override fun complete(): Boolean {
        if (isCompleted()) return true
        return nodes.last.complete()
    }

    override fun toQuery(): String {
        val result = StringBuilder()
        for (node in nodes) {
            result.append(node.toQuery())
        }
        return result.toString()
    }

    inner class ExpressionNode(
            val column: Column<*>,
            private val operatorNode: Node,
            private val valueNode: Node
    ) : Node() {

        init {
            valueNode.setOnCompletedHandler { addColumnNameNode() }
        }

        override fun append(char: Char): Boolean {
            if (isCompleted()) return false

            return if (!operatorNode.isCompleted()) {
                operatorNode.append(char)
            } else {
                valueNode.append(char)
            }
        }

        override fun isCompleted(): Boolean {
            return operatorNode.isCompleted() && valueNode.isCompleted()
        }

        override fun complete(): Boolean {
            if (isCompleted()) return true

            return if (!operatorNode.isCompleted()) {
                operatorNode.complete()
            } else {
                valueNode.complete()
            }
        }

        override fun toQuery(): String {

            if (!operatorNode.isCompleted()) {
                return column.name
            } else {
                val name = column.name
                val operator = operatorNode.toQuery()
                val value = valueNode.toQuery()
                return "$name $operator $value"
            }


        }
    }


    inner class ColumnNameNode(columnNames: List<String>) : OrNode(columnNames)

}
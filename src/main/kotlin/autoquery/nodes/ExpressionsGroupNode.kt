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

class ExpressionsGroupNode(private val columnsVariants: List<Column<*>>) : Node() {

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
                is StringColumn -> nodes.add(ExpressionNode(selectedColumn, StringOperatorNode(), StringValueNode()))
                is BooleanColumn -> nodes.add(ExpressionNode(selectedColumn, BooleanOperatorNode(), BooleanValueNode()))
                is IntColumn -> nodes.add(ExpressionNode(selectedColumn, OperatorNode(), IntValueNode()))
                is FloatColumn -> nodes.add(ExpressionNode(selectedColumn, OperatorNode(), FloatValueNode()))
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

    override fun delete(): Boolean {
        return if (nodes[0].isEmpty()) {
            onDeletedAll(this)
            false
        } else {
            if (isCompleted()) setNotCompleted()
            val lastNode = nodes[nodes.size - 1]
            return if (lastNode.isEmpty()) {
                nodes.removeLast()
                if (nodes.isEmpty()) initGroup()
                true
            } else {
                lastNode.delete()
            }
        }
    }

    private fun initGroup() {
        addColumnNameNode()
    }

    inner class ExpressionNode(
            private val column: Column<*>,
            private val operatorNode: Node,
            private val valueNode: Node
    ) : Node() {

        override fun isEmpty(): Boolean {
            /**
             * Never empty. As column is defined always.
             * If delete called on column it's replaced with [ColumnNameNode]
             */
            return false
        }

        override fun delete(): Boolean {

            if (!valueNode.isEmpty()) {
                return valueNode.delete()
            } else if (!operatorNode.isEmpty()) {
                return operatorNode.delete()
            } else {
                val removedColumn = column.name
                selectedColumns.remove(removedColumn)
                nodes.removeLast()
                addColumnNameNode(removedColumn.substring(0, removedColumn.length - 1))
                return true
            }
        }

        init {
            valueNode.setOnCompletedHandler {
                val union = UnionNode()
                union.setOnCompletedHandler {
                    addColumnNameNode()
                }
                nodes.add(union)
            }
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

            return if (!operatorNode.isCompleted()) {
                column.name
            } else {
                val name = column.name
                val operator = operatorNode.toQuery()
                val value = valueNode.toQuery()
                when {
                    operator.isEmpty() -> {
                        "$name"
                    }
                    value.isEmpty() -> {
                        "$name $operator"
                    }
                    else -> "$name $operator $value"
                }

            }
        }
    }


    inner class ColumnNameNode(columnNames: List<String>) : OrNode(columnNames)

}
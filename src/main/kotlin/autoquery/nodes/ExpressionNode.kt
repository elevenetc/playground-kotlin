package autoquery.nodes

import autoquery.columns.Column

class ExpressionNode(
        private val group: ExpressionsGroup,
        private val column: Column<*>,
        private val operatorNode: Node,
        private val valueNode: Node
) : Node() {

    var cursor = 1

    override fun isEmpty(): Boolean {
        /**
         * Never empty. As column is defined always.
         * If delete called on column it's replaced with [ColumnNameNode]
         */
        return false
    }

    override fun delete(): Boolean {

        return if (cursor == 2) {
            if (valueNode.isEmpty()) {
                cursor = 1
                return true
            }
            valueNode.delete()
            true
        } else {//1

            if (operatorNode.isEmpty()) {
                val removedColumn = column.name
                group.removeLast(removedColumn)
                true
            } else {
                operatorNode.delete()
                true
            }
        }
    }

    init {
        valueNode.setOnCompletedHandler {
            group.addUnion()
        }
    }

    override fun append(char: Char): Boolean {
        if (isCompleted()) return false

        return if (cursor == 1) {
            operatorNode.append(char)
            if (operatorNode.isCompleted()) cursor = 2
            true
        } else {
            valueNode.append(char)
            true
        }
    }

    override fun isCompleted(): Boolean {
        return operatorNode.isCompleted() && valueNode.isCompleted()
    }

    override fun complete(): Boolean {
        if (isCompleted()) return true

        return if (!operatorNode.isCompleted()) {
            cursor = 2
            operatorNode.complete()
        } else {
            valueNode.complete()
        }
    }

    override fun toQuery(): String {

        return if (!operatorNode.isCompleted()) {
            column.name + " "
        } else {
            val name = column.name
            val operator = operatorNode.toQuery()
            val value = valueNode.toQuery()

            //if (operator.isEmpty()) return name

            return if (cursor == 1) {
                "$name $operator"
            } else {
                "$name $operator $value"
            }

        }
    }
}
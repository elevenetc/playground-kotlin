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
         * If deleteChar called on column it's replaced with dynamic search node.
         */
        return false
    }

    override fun deleteChar(): Boolean {

        return if (cursor == 2) {
            if (valueNode.isEmpty()) {
                cursor = 1
                return true
            }
            valueNode.deleteChar()
            true
        } else {//1

            if (operatorNode.isEmpty()) {
                val removedColumn = column.name
                group.removeLast(removedColumn)
                true
            } else {
                operatorNode.deleteChar()
                true
            }
        }
    }

    init {
        valueNode.setOnCompletedHandler {
            group.addUnion()
        }
    }

    override fun addChar(char: Char): Boolean {
        if (isCompleted()) return false

        return if (cursor == 1) {
            operatorNode.addChar(char)
            if (operatorNode.isCompleted()) cursor = 2
            true
        } else {
            valueNode.addChar(char)
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
package autoquery.values

import autoquery.nodes.Node


class IntValueNode : Node() {

    override fun complete(): Boolean {
        return if (!value.isEmpty()) {
            setCompleted()
            true
        } else {
            false
        }
    }

    override fun addChar(char: Char): Boolean {
        val newValue = value.toString() + char
        return try {
            newValue.toInt()
            value.append(char)
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

}
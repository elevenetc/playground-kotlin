package autoquery.nodes.values

import autoquery.Node

class FloatValueNode : Node() {

    override fun complete(): Boolean {
        return if (!value.isEmpty()) {
            setCompleted()
            true
        } else {
            false
        }
    }

    override fun append(char: Char): Boolean {
        var newValue = value.toString() + char
        return try {
            newValue.toFloat()
            value.append(char)
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

}
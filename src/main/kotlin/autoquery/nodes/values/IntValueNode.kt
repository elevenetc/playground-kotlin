package autoquery.nodes.values

import autoquery.Node


class IntValueNode : Node() {

    override fun complete(): Boolean {
        return false
    }

    override fun append(char: Char): Boolean {
        var newValue = value.toString() + char
        return try {
            newValue.toInt()
            value.append(char)
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

}
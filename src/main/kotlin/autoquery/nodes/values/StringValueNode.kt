package autoquery.nodes.values

import autoquery.nodes.Node

class StringValueNode : Node() {

    override fun complete(): Boolean {
        return if (value.isEmpty()) {
            false
        } else {
            setCompleted()
            true
        }
    }

    override fun append(char: Char): Boolean {
        value.append(char)
        return true
    }
}
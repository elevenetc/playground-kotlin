package autoquery.values

import autoquery.nodes.Node

class FloatValueNode : Node() {

    override fun complete(): Boolean {
        return if (!value.isEmpty()) {
            setCompleted()
            true
        } else {
            false
        }
    }

    override fun deleteChar(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addChar(char: Char): Boolean {
        val newValue = value.toString() + char
        return try {
            newValue.toFloat()
            value.append(char)
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

}
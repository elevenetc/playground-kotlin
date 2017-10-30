package autoquery.nodes

import autoquery.countCompletable

open class SingleNode(private val target: String, completed: Boolean = false) : Node() {

    init {
        if (completed) setCompleted(target)
    }

    override fun complete(): Boolean {
        if (isCompleted()) return true
        return if (countCompletable(target, value.toString()) > 0) {
            value.setLength(0)
            value.append(target)
            setCompleted()
            true
        } else {
            false
        }
    }

    override fun addChar(char: Char): Boolean {
        if (isCompleted()) return false
        value.append(char)
        return true
    }
}
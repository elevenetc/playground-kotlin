package autoquery.nodes

import autoquery.countCompletable

open class SingleNode(private val target: String) : Node() {

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

    override fun append(char: Char): Boolean {
        if (isCompleted()) return false
        value.append(char)
        return true
    }
}
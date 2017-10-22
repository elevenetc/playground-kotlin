package autoquery.nodes

import autoquery.deleteLastChar
import autoquery.getFullCompletableIndex
import autoquery.getShortestCompletable
import autoquery.toSimpleString

class AndNode(private val variants: List<String>) : Node() {

    private val completedValues = mutableListOf<String>()

    /**
     * Trying to complete [value].
     * If it's completed then new value is added to [completedValues]
     *
     * @return true when all [variants] are filled in [completedValues]
     */
    override fun complete(): Boolean {
        if (isCompleted()) return true
        return tryToCompleteCurrent()
    }

    override fun append(char: Char): Boolean {
        if (isCompleted()) return false

        return if (char == ',') {
            tryToCompleteCurrent()
        } else {
            value.append(char)

            val index = getFullCompletableIndex(value, variants, completedValues)

            if (index > -1) {
                completedValues.add(value.toString())
                value.setLength(0)
                if (completedValues.size == variants.size) setCompleted()
            }
            true
        }
    }

    override fun delete(): Boolean {
        return if (value.isEmpty() && completedValues.isEmpty()) {
            onDeletedAll(this)
            false
        } else if (value.isEmpty()) {
            val last = completedValues.removeAt(completedValues.size - 1)
            value.append(last)
            delete()
        } else {
            deleteLastChar(value)

            if (isCompleted()) setNotCompleted()

            true
        }
    }

    private fun tryToCompleteCurrent(): Boolean {
        val shortest = getShortestCompletable(value, variants, completedValues)
        return if (shortest.isEmpty()) {
            false
        } else {
            value.setLength(0)
            completedValues.add(shortest)
            if (completedValues.size == variants.size) setCompleted()
            true
        }
    }

    override fun toQuery(): String {
        return toSimpleString(value, completedValues)
    }
}
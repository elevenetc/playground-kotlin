package autoquery

class AndNode(private val variants: List<String>) : Node() {

    override fun complete(): Boolean {

        val shortest = getShortestCompletable(value, variants)

        return if (!shortest.isEmpty()) {
            value.setLength(0)
            value.append(shortest)
            isCompleted = true
            true
        } else {
            false
        }
    }

    override fun append(char: Char) {
        if (isCompleted) return
        value.append(char)

        val index = getFullCompletableIndex(value, variants)

        if (index > -1) {
            isCompleted = true
        }
    }
}
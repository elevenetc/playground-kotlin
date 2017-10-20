package autoquery

class OrNode(val variants: List<String>) : Node() {

    override fun complete(): Boolean {
        if (isCompleted) return true

        val completable = getShortestCompletable(value, variants)

        return if (completable.isEmpty()) {
            false
        } else {
            value.setLength(0)
            value.append(completable)
            isCompleted = true
            true
        }
    }

    override fun append(char: Char):Boolean {
        if (isCompleted) return false
        value.append(char)

        if (variants.contains(value.toString())) {
            isCompleted = true
        }
        return true
    }

}
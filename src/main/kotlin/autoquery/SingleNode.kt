package autoquery

class SingleNode(private val target: String) : Node() {

    override fun complete(): Boolean {
        if (isCompleted) return true
        return if (countCompletable(target, value.toString()) > 0) {
            value.setLength(0)
            value.append(target)
            true
        } else {
            false
        }
    }

    override fun append(char: Char) {
        if (isCompleted) return
        value.append(char)
        if (value.toString() == target) {
            isCompleted = true
        }
    }
}
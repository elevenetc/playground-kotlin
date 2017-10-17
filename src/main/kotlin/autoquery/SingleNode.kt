package autoquery

class SingleNode(private val target: String) : Node() {

    override fun complete(): Boolean {
        if (countCompletable(target, value.toString()) > 0) {
            value.setLength(0)
            value.append(target)
            return true
        } else {
            return false
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
package autoquery

class BlockingNode : Node() {
    override fun append(char: Char): Boolean {
        return false
    }

    override fun complete(): Boolean {
        return false
    }

}
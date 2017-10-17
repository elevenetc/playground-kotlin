package autoquery

class OrNode(val variants: List<String>) : Node() {

    override fun complete(): Boolean {
        return false
    }

    override fun append(char: Char) {
        value.append(char)
    }

}
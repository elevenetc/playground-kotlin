package autoquery

abstract class Node {

    var completeOnAppend = false
    var blockInvalidCharsOnAppend = false

    private var isCompleted: Boolean = false
    var value: StringBuilder = StringBuilder()
    var onCompleteHandler: (node: Node) -> Unit = {}

    /**
     * @return true if char was appended to current value
     */
    abstract fun append(char: Char): Boolean

    /**
     * @return true if value is completed and nothing could be appended
     */
    abstract fun complete(): Boolean

    fun setOnCompletedHandler(onCompletedHandler: (Node) -> Unit): Node {
        onCompleteHandler = onCompletedHandler
        return this
    }

    open fun isCompleted(): Boolean {
        return isCompleted
    }

    open fun toQuery(): String {
        return value.toString()
    }

    fun setCompleted(value: String) {
        this.value.setLength(0)
        this.value.append(value)
        setCompleted()
    }

    open fun setCompleted() {
        isCompleted = true
        onCompleteHandler(this)
    }
}
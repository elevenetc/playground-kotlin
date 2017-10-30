package autoquery.nodes

abstract class Node {

    var completeOnAppend = false
    var blockInvalidCharsOnAppend = false
    var value: StringBuilder = StringBuilder()
    var onCompleteHandler: (node: Node) -> Unit = {}
    var onNotCompleteHandler: (node: Node) -> Unit = {}
    var onDeletedAll: (node: Node) -> Unit = {}

    private var isCompleted: Boolean = false

    /**
     * @return true if char was appended to current value
     * @return false if char was not added because either is already completed
     * or value with this char is invalid
     */
    abstract fun addChar(char: Char): Boolean

    open fun deleteChar(): Boolean {

        if (value.isEmpty()) {
            onDeletedAll(this)
            return false
        }

        value.deleteCharAt(value.length - 1)

        if (isCompleted()) setNotCompleted()

        return true
    }

    /**
     * When called it should be decided if node is completed and called [setCompleted]
     *
     * @return true if value is completed and nothing could be appended
     * or
     * @return true if cursor could be moved to a next node
     */
    abstract fun complete(): Boolean

    fun setOnCompletedHandler(onCompletedHandler: (Node) -> Unit): Node {
        onCompleteHandler = onCompletedHandler
        return this
    }

    /**
     * @return true if no chars could be added
     */
    open fun isCompleted(): Boolean {
        return isCompleted
    }

    /**
     * @return part of valid SQLite query
     */
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

    fun setNotCompleted() {
        isCompleted = false
        onNotCompleteHandler(this)
    }

    open fun isEmpty(): Boolean {
        return value.isEmpty()
    }
}
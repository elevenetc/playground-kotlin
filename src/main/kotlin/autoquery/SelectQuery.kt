package autoquery

import java.util.*

class SelectQuery(vararg tables: Table) : Query {

    private val queue: List<Node> = LinkedList()

    override fun append(char: Char) {

    }

    override fun complete() {

    }

    override fun getCurrent(): List<Node> {
        return queue
    }
}
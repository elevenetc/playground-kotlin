package autoquery

import java.util.*

class Query(val message: String) {

    val next: LinkedList<Query> = LinkedList()
    var root: Query? = null

    fun then(node: Query): Query {
        next.add(node)
        return node
    }

    fun then(message: String): Query {
        return then(Query(message))
    }

    fun from(vararg nodes: Query): Query {
        for (node in nodes) {
            node.root = root
        }
        Collections.addAll<Query>(next, *nodes)
        return this
    }

    fun withColumns(vararg fields: String): Query {
        for (field in fields) {
            val column = Query(field)
            column.root = root
            next.add(column)
        }
        return this
    }

    fun where(): Query {
        //next - tables
        //tables.next = where
        val result = Query("where")
        result.root = root
        next.add(result)
        return result
    }


    fun selectColumns(): Query {
        val result = Query("fieldsSelector")
        result.root = root
        next.add(result)
        return result
    }

    fun build(): Query? {
        return this.root
    }

    override fun toString(): String {
        return "Query(message='$message', next=$next)"
    }


}


fun select(vararg tables: Query): Query {
    val select = Query("select")
    select.root = select
    for (node in tables) {
        node.root = select
        select.next.add(node)
    }
    return select
}

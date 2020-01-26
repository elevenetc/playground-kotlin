package autocomplete

import java.util.*

class AutoCompleter {

    private val tree = AutoCompleteTree()
    private val chars = StringBuilder()
    private val currentNodes = LinkedList<Node>()

    fun setRoot(vararg root: Node) {
        tree.nodes.clear()
        tree.nodes.addAll(root)
    }

    fun next(): List<Node> {
        return if (currentNodes.isEmpty()) emptyList()
        else currentNodes.first.children()
    }

    fun set(root: List<Node>,
            next1: List<Node>,
            next2: List<Node> = emptyList(),
            next3: List<Node> = emptyList(),
            next4: List<Node> = emptyList()) {

        setRoot(root.first())

        root.forEach {
            it.children().addAll(next1)
        }

        next1.forEach {
            it.children().addAll(next2)
        }

        next2.forEach {
            it.children().addAll(next3)
        }

        next3.forEach {
            it.children().addAll(next4)
        }
    }

    fun append(ch: Char) {
        chars.append(ch)
    }

    fun nodes(): List<Node> {
        return currentNodes
    }

    fun complete(): Boolean {
        return complete(false)
    }

    private fun complete(full: Boolean): Boolean {

        if (chars.isEmpty()) return false

        val n: Node? = if (currentNodes.isEmpty()) {

            //first complete case

            tree.nodes.firstOrNull {

                if (it is AnyNode) return true

                val nodeValue = it.value()
                val prefix = chars.toString()

                if (full) {
                    nodeValue == prefix
                } else {
                    nodeValue.startsWith(prefix)
                }


            }
        } else {

            val last = currentNodes.last

            val result = last.children().firstOrNull {

                var r = false

                if (it is AnyNode) {
                    r = true
                } else {
                    if (full) {
                        r = it.value() == chars.toString()
                    } else {
                        r = it.value().startsWith(chars.toString())
                    }
                }



                r
            }

            if (result is AnyNode) {
                val anyNode = AnyNode(chars.toString())
                anyNode.children().addAll(result.children())
                anyNode
            } else {
                result
            }
        }

        return if (n == null) {
            false
        } else {
            currentNodes.add(n)
            chars.clear()
            true
        }
    }
}

class AutoCompleteTree {
    val nodes: MutableList<Node> = mutableListOf()
}

abstract open class Node {

    private val children = mutableListOf<Node>()
    private var future: () -> MutableList<Node> = { mutableListOf() }

    fun children(): MutableList<Node> {
        val f = future()
        return if (f.isEmpty()) {
            children
        } else {
            f
        }
    }

    fun addNext(future: () -> MutableList<Node>): Node {
        this.future = future
        return this
    }

    fun addNext(nodes: List<Node>): Node {
        nodes.forEach {
            children.add(it)
        }
        return this
    }

    fun add(nodes: List<Node>): Node {
        children.addAll(nodes)
        return this
    }

    fun add(vararg node: Node): Node {
        children.addAll(node)
        return this
    }

    abstract fun value(): String

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Node

        return other.value() == value()
    }

    override fun hashCode(): Int {
        return value().hashCode()
    }

    override fun toString(): String {
        return "[" + value() + "]"
    }
}

class StringNode(val value: String) : Node() {

    override fun value(): String {
        return this.value
    }
}

class AnyNode() : Node() {

    private var v: String = ""

    constructor(v: String) : this() {
        this.v = v
    }

    override fun value(): String {
        return v
    }

}
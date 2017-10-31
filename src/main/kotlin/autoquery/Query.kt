package autoquery

import autoquery.nodes.Node

interface Query {
    fun addChar(char: Char)
    fun deleteChar(): Boolean
    fun complete(): Boolean
    fun getNodes(): List<Node>
    fun cursorPosition(): Int
    fun toQuery(): String
}
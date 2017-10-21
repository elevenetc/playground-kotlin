package autoquery

import autoquery.nodes.Node

interface Query {
    fun append(char: Char)
    fun complete()
    fun getCurrent(): List<Node>
    fun currentIndex(): Int
    fun toQuery():String
}
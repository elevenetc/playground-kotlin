package main

/**
 * Created by eugene.levenetc on 24/05/2017.
 */
class Node(value: Int) {

    var value = value

    var top: Node? = null
    var bottom: Node? = null
    var left: Node? = null
    var right: Node? = null
    var topRight: Node? = null
    var bottomRight: Node? = null
    var topLeft: Node? = null
    var bottomLeft: Node? = null

    var children: Array<Node?> = arrayOfNulls(8)


    override fun toString(): String {
        return "$value"
    }
}
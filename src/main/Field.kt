package main

/**
 * Created by eugene.levenetc on 24/05/2017.
 */
class Field(width: Int, height: Int) {

    var matrix = Array(width) { arrayOfNulls<Node>(height) }

    init {

        var prev: Node? = null
        var index = 0
        for (c in 0..width - 1) {
            for (r in 0..height - 1) {
                matrix[c][r] = Node(index)
                matrix[c][r]?.left = prev
                prev = matrix[c][r]

                index++
            }
        }
    }
}
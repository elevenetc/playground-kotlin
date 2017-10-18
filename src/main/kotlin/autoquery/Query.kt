package autoquery

interface Query {
    fun append(char: Char)
    fun complete()
    fun getCurrent(): List<Node>
    fun currentIndex(): Int
    fun getSimpleName():String
}
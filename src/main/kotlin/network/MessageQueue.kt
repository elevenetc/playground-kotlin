package network

interface MessageQueue {
    fun get(): String
    fun add(message: String)
    fun size(): Int
}
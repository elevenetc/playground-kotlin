package network

interface ConnectionFactory {
    fun create(): Connection
}
package network

interface InOut {
    fun write(value: String)
    fun read(): String
}
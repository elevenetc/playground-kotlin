package reactive.networks

interface ISocket {
    fun read(): String
    fun write(data: String)
}
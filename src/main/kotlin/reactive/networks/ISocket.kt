package reactive.networks

interface ISocket {
    fun readLine(): String
    fun println(str: String)
}
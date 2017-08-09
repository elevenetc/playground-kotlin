package threading

interface Scheduler {
    fun thread(action: () -> Unit): IThread
    fun sleepFor(duration: Long)
    fun sleepForever()
}
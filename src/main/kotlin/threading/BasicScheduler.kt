package threading

class BasicScheduler : Scheduler {

    override fun sleepForever() {
        Thread.sleep(Long.MAX_VALUE)
    }

    override fun sleepFor(duration: Long) {
        Thread.sleep(duration)
    }

    override fun thread(action: () -> Unit): IThread {
        return ThreadImpl(action)
    }

    class ThreadImpl(action: () -> Unit) : IThread {

        val thread: Thread = Thread({
            action()
        })

        override fun start() {
            thread.start()
        }

        override fun interrupt() {
            thread.interrupt()
        }

    }
}
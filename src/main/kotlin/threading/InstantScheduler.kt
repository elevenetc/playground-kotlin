package threading

class InstantScheduler : Scheduler {

    override fun sleepFor(duration: Long) {

    }

    override fun sleepForever() {

    }

    override fun thread(action: () -> Unit): IThread {
        return object : IThread {
            override fun start() {
                action()
            }

            override fun interrupt() {

            }

        }
    }

}
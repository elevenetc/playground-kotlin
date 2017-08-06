package utils

fun sleepForever() {
    Thread.sleep(Long.MAX_VALUE)
}

fun sleepFor(delay: Long) {
    Thread.sleep(delay)
}
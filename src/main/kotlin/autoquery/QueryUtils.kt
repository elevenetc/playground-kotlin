package autoquery

fun getFullCompletableIndex(value: StringBuilder, variants: List<String>): Int {
    return getFullCompletableIndex(value.toString(), variants)
}

fun getFullCompletableIndex(value: String, targets: List<String>): Int {
    return getFullCompletableIndex(value, *targets.toTypedArray())
}

fun getFullCompletableIndex(value: String, vararg targets: String): Int {
    for (i in 0 until targets.size) {
        if (targets[i] == value) {
            return i
        }
    }
    return -1
}

fun getShortestCompletable(value: StringBuilder, variants: List<String>): String {
    return getShortestCompletable(value.toString(), variants)
}

fun getShortestCompletable(value: String, variants: List<String>): String {
    return getShortestCompletable(value, *variants.toTypedArray())
}

fun getShortestCompletable(value: String, vararg variants: String): String {
    var result = ""
    var shortest = Integer.MAX_VALUE
    for (target in variants) {
        val length = countCompletable(target, value)
        if (length != 0 && length < shortest) {
            shortest = length
            result = target
        }
    }
    return result
}

fun countCompletable(target: String, value: String): Int {
    if (value.isEmpty()) return 0
    if (target.isEmpty()) return 0
    if (value.length > target.length) return 0

    var result = 0
    for (i in 0 until value.length) {
        val newValueChar = target[i]
        val valueChar = value[i]

        if (valueChar == newValueChar) {
            result++
        } else {
            return 0
        }
    }
    return result
}

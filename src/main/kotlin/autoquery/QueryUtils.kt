package autoquery

fun getFullCompletableIndex(value: StringBuilder, variants: List<String>, ignore: List<String> = emptyList()): Int {
    return getFullCompletableIndex(value.toString(), variants)
}

fun getFullCompletableIndex(value: String, variants: List<String>, ignore: List<String> = emptyList()): Int {
    for (i in 0 until variants.size) {
        if (ignore.contains(variants[i])) continue
        if (variants[i] == value) {
            return i
        }
    }
    return -1
}

fun getShortestCompletable(value: StringBuilder, variants: List<String>, ignore: List<String> = emptyList()): String {
    return getShortestCompletable(value.toString(), variants, ignore)
}

fun getShortestCompletable(value: String, variants: List<String>, ignore: List<String> = emptyList()): String {
    var result = ""
    var shortest = Integer.MAX_VALUE
    for (target in variants) {

        if (ignore.contains(target)) continue

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

fun toSimpleString(value: StringBuilder, completedValues: List<String>): String {
    val builder = StringBuilder()
    if (completedValues.isNotEmpty()) {
        for (i in 0 until completedValues.size) {
            val completed = completedValues[i]
            builder.append(completed)

            if (i != completedValues.size - 1) {
                builder.append(", ")
            }
        }
    }

    if (!value.isEmpty()) {
        builder.append(", ")
        builder.append(value.toString())
    }

    return builder.toString()
}

fun toSimpleString(nodes: List<Node>): String {
    val result = StringBuilder()
    val nonEmpty = mutableListOf<String>()

    for (i in 0 until nodes.size) {
        val node = nodes[i]
        val name = node.simpleName()
        if (!name.isEmpty()) nonEmpty.add(name)
    }

    for (i in 0 until nonEmpty.size) {
        result.append(nonEmpty[i])

        if (i != nonEmpty.size - 1) result.append(", ")
    }

    return result.toString()
}
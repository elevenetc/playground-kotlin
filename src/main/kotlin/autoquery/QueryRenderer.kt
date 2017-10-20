package autoquery

fun toQuery(node: ExpressionsNode): String {
    val result = StringBuilder()
    for (i in 0 until node.addedColumns.size) {
        val column = node.addedColumns[i]

        result.append(column.name)
        result.append(" = ")
        result.append(column.stringValue())

        if (i != node.addedColumns.size - 1) {
            val union = column.union
            result.append(" $union ")
        }
    }

    if (node.newColumnName.isNotEmpty()) {
        if (result.isEmpty()) {
            result.append(node.newColumnName.toString())
        } else {
            result.append(" ")
            result.append(node.newColumnName.toString())
        }
    }

    return result.toString()
}
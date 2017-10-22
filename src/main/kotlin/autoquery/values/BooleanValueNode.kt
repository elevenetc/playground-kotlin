package autoquery.values

import autoquery.nodes.OrNode

class BooleanValueNode : OrNode(listOf("false", "true"))
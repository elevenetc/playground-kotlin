package autoquery.nodes.values

import autoquery.nodes.OrNode

class BooleanValueNode : OrNode(listOf("false", "true"))
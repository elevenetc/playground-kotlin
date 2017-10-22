package autoquery.operators

import autoquery.nodes.OrNode

class BooleanOperatorNode : OrNode(listOf("=", "!="))
package autoquery.nodes.operators

import autoquery.nodes.OrNode

class BooleanOperatorNode : OrNode(listOf("=", "!="))
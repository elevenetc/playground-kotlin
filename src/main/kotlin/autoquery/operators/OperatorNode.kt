package autoquery.operators

import autoquery.nodes.OrNode

class OperatorNode : OrNode(listOf("=", "!=", "<", ">", ">=", "<="))
package autoquery.nodes.operators

import autoquery.OrNode

class OperatorNode : OrNode(listOf("=", "!=", "<", ">", ">=", "<="))
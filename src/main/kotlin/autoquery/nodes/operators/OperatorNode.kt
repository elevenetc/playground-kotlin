package autoquery.nodes.operators

import autoquery.nodes.OrNode

class OperatorNode : OrNode(listOf("=", "!=", "<", ">", ">=", "<="))
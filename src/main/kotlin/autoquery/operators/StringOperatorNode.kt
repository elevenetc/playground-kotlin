package autoquery.operators

import autoquery.nodes.OrNode

class StringOperatorNode : OrNode(listOf("=", "!="))
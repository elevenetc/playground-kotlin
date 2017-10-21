package autoquery.nodes.operators

import autoquery.nodes.OrNode

class StringOperatorNode : OrNode(listOf("=", "!="))
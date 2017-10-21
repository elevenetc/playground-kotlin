package autoquery.nodes

import autoquery.OrNode

class UnionNode:OrNode(listOf("or", "and"))
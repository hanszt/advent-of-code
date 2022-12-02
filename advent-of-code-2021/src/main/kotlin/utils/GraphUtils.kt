package utils

import model.Node
import model.WeightedNode

fun List<String>.toBiDiGraph(delimiter: String): Map<String, Node<String?>> = toBiDiGraph(delimiter) { it }

fun List<String>.toBiDiGraph(delimiter: Char): Map<String, Node<String?>> = toBiDiGraph(delimiter.toString())

inline fun <T> List<String>.toBiDiGraph(delimiter: String, mapper: (String) -> T): Map<String, Node<T>> {
    val nodes = mutableMapOf<String, Node<T>>()
    for (line in this) {
        val (value, other) = line.split(delimiter)
        val node = nodes.getOrDefault(value, Node(mapper(value)))
        val otherNode = nodes.getOrDefault(other, Node(mapper(other)))
        node.addNeighbor(otherNode)
        otherNode.addNeighbor(node)
        nodes[value] = node
        nodes[other] = otherNode
    }
    return nodes
}

fun <T> Array<IntArray>.toWeightedGraph(directions: List<Pair<Int, Int>>): Map<Pair<Int, Int>, WeightedNode<T>> {
    val graph = mutableMapOf<Pair<Int, Int>, WeightedNode<T>>()
    forEachPoint { x, y ->
        val curNode = graph.computeIfAbsent(x to y) { WeightedNode(weight = this[y][x]) }
        directions.map { (dx, dy) -> (x + dx) to (y + dy) }
            .mapNotNull { (nx, ny) ->
                getOrNull(ny)
                    ?.getOrNull(nx)
                    ?.let { graph.computeIfAbsent(nx to ny) { WeightedNode(weight = this[ny][nx]) } }
            }.forEach(curNode::addNeighbor)
    }
    return graph
}


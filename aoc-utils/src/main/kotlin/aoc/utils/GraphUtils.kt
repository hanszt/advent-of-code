package aoc.utils

import aoc.utils.model.GridPoint2D
import aoc.utils.model.GridPoint2D.Companion.by
import aoc.utils.model.Node
import aoc.utils.model.WeightedNode

fun List<String>.toBiDiGraph(delimiter: String): Map<String, Node<String>> = toBiDiGraph(delimiter) { it }

fun <R> List<String>.toBiDiGraph(delimiter: String, mapper: (String) -> R): Map<String, Node<R>> = toBiDiGraph(
    { it.split(delimiter).toEnds() },
    mapper
)

fun List<String>.toBiDiGraph(delimiter: Char): Map<String, Node<String>> = toBiDiGraph(
    { it.split(delimiter).toEnds() },
    { it })

inline fun <T, K, R> List<T>.toBiDiGraph(toPairMapper: (T) -> Pair<K, K>, mapper: (K) -> R): Map<K, Node<R>> = buildMap {
    for (item in this@toBiDiGraph) {
        val (value, other) = toPairMapper(item)
        val node = getOrDefault(value, Node(mapper(value)))
        val otherNode = getOrDefault(other, Node(mapper(other)))
        node.addNeighbor(otherNode)
        otherNode.addNeighbor(node)
        this[value] = node
        this[other] = otherNode
    }
}

fun <T> Array<IntArray>.toWeightedGraph(
    directions: List<GridPoint2D>,
    computeWeight: (Int, Int) -> Int = { x, y -> this[y][x] },
    computeValue: (Int, Int) -> T? = { _, _ -> null }
): Map<GridPoint2D, WeightedNode<T>> = buildMap {
    forEachPoint { x, y ->
        val curNode = computeIfAbsent(x by y) { WeightedNode(computeValue(x, y), weight = computeWeight(x, y)) }
        directions.map { (dx, dy) -> (x + dx) by (y + dy) }
            .mapNotNull { (nx, ny) ->
                getOrNull(ny)
                    ?.getOrNull(nx)
                    ?.let { computeIfAbsent(nx by ny) { WeightedNode(computeValue(nx, ny), weight = computeWeight(nx, ny)) } }
            }.forEach(curNode::addNeighbor)
    }
}


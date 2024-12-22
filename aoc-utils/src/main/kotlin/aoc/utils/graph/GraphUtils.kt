package aoc.utils.graph

import aoc.utils.grid2d.GridPoint2D
import aoc.utils.grid2d.forEachPoint
import aoc.utils.grid2d.get
import aoc.utils.grid2d.getOrNull
import aoc.utils.toEnds

fun List<String>.toBiDiGraph(delimiter: String): Map<String, MutableNode<String>> = toBiDiGraph(delimiter) { it }

fun <R> List<String>.toBiDiGraph(delimiter: String, mapper: (String) -> R): Map<String, MutableNode<R>> = toBiDiGraph(
    { it.split(delimiter).toEnds() },
    mapper
)

fun List<String>.toBiDiGraph(delimiter: Char): Map<String, MutableNode<String>> = toBiDiGraph(
    { it.split(delimiter).toEnds() },
    { it })

inline fun <T, K, R> List<T>.toBiDiGraph(toPairMapper: (T) -> Pair<K, K>, mapper: (K) -> R): Map<K, MutableNode<R>> = buildMap {
    for (item in this@toBiDiGraph) {
        val (value, other) = toPairMapper(item)
        val node = getOrDefault(value, MutableNode(mapper(value)))
        val otherNode = getOrDefault(other, MutableNode(mapper(other)))
        node.addNeighbor(otherNode)
        otherNode.addNeighbor(node)
        this[value] = node
        this[other] = otherNode
    }
}

fun <T> Array<IntArray>.toWeightedGraph(
    directions: List<GridPoint2D>,
    computeWeight: (GridPoint2D) -> Int = { this[it] },
    computeValue: (GridPoint2D) -> T? = { null }
): Map<GridPoint2D, WeightedNode<T>> = buildMap {
    forEachPoint { p ->
        val curNode = computeIfAbsent(p) { WeightedNode(computeValue(p), weight = computeWeight(p)) }
        directions.mapNotNull {
            val n = p + it
            getOrNull(n)?.let { computeIfAbsent(n) { WeightedNode(computeValue(n), weight = computeWeight(n)) } }
        }.forEach(curNode::addNeighbor)
    }
}


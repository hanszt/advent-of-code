package aoc.utils.model

import java.util.*

class WeightedNode<T>(val value: T? = null, val weight: Int) {

    var distance = Int.MAX_VALUE
    var shortestPath: List<WeightedNode<T>> = LinkedList()
    val neighbors = mutableSetOf<WeightedNode<T>>()

    fun addNeighbor(neighbor: WeightedNode<T>) = neighbors.add(neighbor)

    fun updateShortestPath(neighbor: WeightedNode<T>) {
        val neighborDistance = neighbor.distance + weight
        if (neighborDistance < distance) {
            distance = neighborDistance
            shortestPath = neighbor.shortestPath.plus(neighbor)
        }
    }
}

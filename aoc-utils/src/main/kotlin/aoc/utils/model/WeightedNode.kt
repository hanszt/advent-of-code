package aoc.utils.model

import java.util.*

class WeightedNode<T>(value: T? = null, val weight: Int) : Node<T>(value) {

    var distance = Int.MAX_VALUE
    var shortestPath: List<WeightedNode<T>> = LinkedList()

    fun updateShortestPath(neighbor: WeightedNode<T>) {
        val neighborDistance = neighbor.distance + weight
        if (neighborDistance < distance) {
            distance = neighborDistance
            shortestPath = neighbor.shortestPath.plus(neighbor)
        }
    }
}

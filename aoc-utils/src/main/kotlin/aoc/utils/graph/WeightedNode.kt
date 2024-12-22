package aoc.utils.graph

import java.util.*

class WeightedNode<T>(
    val value: T? = null,
    val weight: Int,
    val neighbors: MutableSet<WeightedNode<T>> = HashSet(),
    override val prev: WeightedNode<T>? = null
): Node<WeightedNode<T>> {

    var cost = Int.MAX_VALUE
    private var leastCostPathInternal: List<WeightedNode<T>> = emptyList()
    val leastCostPath: List<WeightedNode<T>> get() = Collections.unmodifiableList(leastCostPathInternal)

    fun addNeighbor(neighbor: WeightedNode<T>) = neighbors.add(neighbor)

    fun updateShortestPath(neighbor: WeightedNode<T>) {
        val neighborCost = neighbor.cost + weight
        if (neighborCost < cost) {
            cost = neighborCost
            leastCostPathInternal = neighbor.leastCostPathInternal + neighbor
        }
    }

    override fun toString(): String = "WeightedNode(value=$value, weight=$weight, distance=$cost)"
}
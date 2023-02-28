package aoc.utils.model

class WeightedNode<T>(val value: T? = null,
                      val weight: Int,
                      val neighbors: MutableSet<WeightedNode<T>> = HashSet()) {

    var cost = Int.MAX_VALUE
    var leastCostPath: List<WeightedNode<T>> = emptyList()


    fun addNeighbor(neighbor: WeightedNode<T>) = neighbors.add(neighbor)

    fun updateShortestPath(neighbor: WeightedNode<T>) {
        val neighborCost = neighbor.cost + weight
        if (neighborCost < cost) {
            cost = neighborCost
            leastCostPath = neighbor.leastCostPath.plus(neighbor)
        }
    }

    override fun toString(): String = "WeightedNode(value=$value, weight=$weight, distance=$cost)"
}

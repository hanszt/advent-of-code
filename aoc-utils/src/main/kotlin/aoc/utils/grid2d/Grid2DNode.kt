package aoc.utils.grid2d

import aoc.utils.graph.Node

data class Grid2DNode
@JvmOverloads
constructor(
    val position: GridPoint2D,
    val cost: Int = 0,
    override val prev: Grid2DNode? = null,
) : Node<Grid2DNode> {
    override fun equals(other: Any?): Boolean = this === other || (other is Grid2DNode && cost == other.cost && position == other.position)
    override fun hashCode(): Int = arrayOf(cost, position).contentHashCode()
}

fun GridPoint2D.withCost(cost: Int): Grid2DNode = Grid2DNode(position = this, cost)

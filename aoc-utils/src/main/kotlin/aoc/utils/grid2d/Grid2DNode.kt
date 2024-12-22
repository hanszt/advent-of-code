package aoc.utils.grid2d

import aoc.utils.graph.Node

data class Grid2DNode(
    val position: GridPoint2D,
    val cost: Int = 0,
    override val prev: Grid2DNode? = null,
) : Node<Grid2DNode>

fun GridPoint2D.withDistance(dist: Int): Grid2DNode = Grid2DNode(position = this, dist)

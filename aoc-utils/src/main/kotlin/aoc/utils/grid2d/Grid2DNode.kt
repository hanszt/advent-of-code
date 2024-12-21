package aoc.utils.grid2d

data class Grid2DNode(
    val position: GridPoint2D,
    val dist: Int = 0,
    val prev: Grid2DNode? = null
) {

    /**
     * Backtracks a path formed by all nodes linked to this node
     */
    fun path(): Sequence<Grid2DNode> = sequence {
        var current = this@Grid2DNode
        while (true) {
            yield(current)
            current = current.prev ?: break
        }
    }
}

fun GridPoint2D.withDistance(dist: Int): Grid2DNode = Grid2DNode(position = this, dist)

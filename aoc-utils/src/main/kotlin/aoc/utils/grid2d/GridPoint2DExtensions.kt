package aoc.utils.grid2d

/**
 * A function to make sure the coordinate jumps to the other side of the grid when over the edge.
 */
fun GridPoint2D.mod(width: Int, height: Int): GridPoint2D = GridPoint2D(x.mod(width), y.mod(height))
fun GridPoint2D.mod(dimension2D: Dimension2D): GridPoint2D = GridPoint2D(x.mod(dimension2D.width), y.mod(dimension2D.height))
fun GridPoint2D.orthoNeighbors(): List<GridPoint2D> = GridPoint2D.orthoDirs.map(::plus)
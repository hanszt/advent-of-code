package aoc.utils.grid2d

/**
 * An unmodifiable version of an IntGrid.
 */
interface IntGrid : Dimension2D {
     fun getOrNull(p2: GridPoint2D): Int?
     operator fun get(p2: GridPoint2D): Int
}

private class StandardIntGrid(private val grid: Array<IntArray>) : IntGrid {

    override val width: Int = grid.getOrNull(0)?.size ?: 0
    override val height: Int = grid.size

    override fun getOrNull(p2: GridPoint2D): Int? = grid.getOrNull(p2.y)?.getOrNull(p2.x)
    override operator fun get(p2: GridPoint2D): Int = grid[p2.y][p2.x]

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is StandardIntGrid) return false

        if (width != other.width) return false
        if (height != other.height) return false
        if (!grid.contentDeepEquals(other.grid)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = width
        result = 31 * result + height
        result = 31 * result + grid.contentDeepHashCode()
        return result
    }

    override fun toString(): String = grid.gridAsString()
}

fun buildIntGrid(width: Int, height: Int, transform: (Int, Int) -> Int): IntGrid =
    StandardIntGrid(Array(height) { y -> IntArray(width) { x -> transform(x, y) } })

fun emptyIntGrid(): IntGrid = StandardIntGrid(emptyArray())

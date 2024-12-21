package aoc.utils.grid2d

/**
 * An unmodifiable Grid 2D.
 */
interface Grid<T> : Dimension2D {
    fun getOrNull(p2: GridPoint2D): T?
    operator fun get(p2: GridPoint2D): T
}

@Suppress("UNCHECKED_CAST")
private class StandardGrid<T>(private val grid: Array<Array<Any>>) : Grid<T> {

    override val width: Int = grid.getOrNull(0)?.size ?: 0
    override val height: Int = grid.size

    override fun getOrNull(p2: GridPoint2D): T? = grid.getOrNull(p2.y)?.getOrNull(p2.x) as T?
    override operator fun get(p2: GridPoint2D): T = grid[p2.y][p2.x] as T

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is StandardGrid<*>) return false

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

fun <T> buildGrid(width: Int, height: Int, transform: (Int, Int) -> T): Grid<T> =
    StandardGrid(Array(height) { y ->
        Array(width) { x -> transform(x, y) as Any }
    })

fun <T> buildGrid(dimension2D: Dimension2D, transform: (GridPoint2D) -> T): Grid<T> = buildGrid(
    width = dimension2D.width,
    height = dimension2D.height,
    transform = { x, y -> transform(gridPoint2D(x, y)) }
)

fun <T> emptyGrid(): Grid<T> = StandardGrid(emptyArray())

package aoc.utils.grid2d

/**
 * An unmodifiable Grid 2D.
 */
interface Grid<T> : Dimension2D, Iterable<List<T>> {

    fun getOrNull(x: Int, y: Int): T?
    fun getOrNull(point: GridPoint2D): T? = getOrNull(point.x, point.y)

    operator fun get(x: Int, y: Int): T
    operator fun get(point: GridPoint2D): T = get(point.x, point.y)

    /**
     * Returns an iterator that iterates over the rows of the grid
     */
    override fun iterator(): Iterator<List<T>>
}

@Suppress("UNCHECKED_CAST")
private class StandardGrid<T>(private val grid: Array<Array<Any>>) : Grid<T> {

    override val width: Int = grid.getOrNull(0)?.size ?: 0
    override val height: Int = grid.size

    override fun get(x: Int, y: Int): T = grid[y][x] as T

    override fun iterator(): Iterator<List<T>> = object : Iterator<List<T>> {
        var y = 0
        override fun hasNext(): Boolean = y < height
        override fun next(): List<T> {
            return if (hasNext()) {
                listOf(*grid[y++] as Array<T>)
            } else throw NoSuchElementException()
        }
    }


    override fun getOrNull(x: Int, y: Int): T? = grid.getOrNull(y)?.getOrNull(x) as T?

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

fun <T> emptyGrid(): Grid<T> = StandardGrid(emptyArray())

package aoc.utils.grid2d

import aoc.utils.GridPoint2DProgressionIterator

/**
 * A progression of values of type [GridPoint2D].
 */
open class GridPoint2DProgression internal constructor(
    start: GridPoint2D,
    endInclusive: GridPoint2D,
) : Iterable<GridPoint2D> {

    /**
     * The first element in the progression.
     */
    val first = start

    /**
     * The last element in the progression.
     */
    val last = endInclusive

    override fun iterator(): Iterator<GridPoint2D> = GridPoint2DProgressionIterator(first, last)

    override fun equals(other: Any?): Boolean =
        other is GridPoint2DProgression && first == other.first && last == other.last

    override fun hashCode(): Int {
        var result = first.hashCode()
        result = 31 * result + last.hashCode()
        return result
    }

    override fun toString(): String = "$first..$last"
}
package aoc.utils

import aoc.utils.model.GridPoint3D

/**
 * A progression of values of type [GridPoint3D].
 */
open class GridPoint3DProgression internal constructor(
    start: GridPoint3D,
    endInclusive: GridPoint3D,
) : Iterable<GridPoint3D> {

    /**
     * The first element in the progression.
     */
    val first = start

    /**
     * The last element in the progression.
     */
    val last = endInclusive

    override fun iterator(): Iterator<GridPoint3D> = GridPoint3DProgressionIterator(first, last)

    override fun equals(other: Any?): Boolean =
        other is GridPoint3DProgression && first == other.first && last == other.last

    override fun hashCode(): Int {
        var result = first.hashCode()
        result = 31 * result + last.hashCode()
        return result
    }

    override fun toString(): String = "$first..$last"
}

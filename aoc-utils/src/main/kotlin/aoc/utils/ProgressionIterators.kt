package aoc.utils

import aoc.utils.model.GridPoint2D
import aoc.utils.model.GridPoint3D

/**
 * An iterator over a progression of values of type [GridPoint2D].
 */
internal class GridPoint2DProgressionIterator(first: GridPoint2D, private val last: GridPoint2D) : Iterator<GridPoint2D> {
    private var hasNext: Boolean = first != last
    private var next: GridPoint2D = if (hasNext) first else last

    override fun hasNext(): Boolean = hasNext

    override fun next(): GridPoint2D {
        val value = next
        if (value == last) {
            if (!hasNext) throw kotlin.NoSuchElementException()
            hasNext = false
        } else {
            next += (last - next).sign
        }
        return value
    }
}

/**
 * An iterator over a progression of values of type [GridPoint3D].
 */
internal class GridPoint3DProgressionIterator(first: GridPoint3D, private val last: GridPoint3D) : Iterator<GridPoint3D> {
    private var hasNext: Boolean = first != last
    private var next: GridPoint3D = if (hasNext) first else last

    override fun hasNext(): Boolean = hasNext

    override fun next(): GridPoint3D {
        val value = next
        if (value == last) {
            if (!hasNext) throw kotlin.NoSuchElementException()
            hasNext = false
        } else {
            next += (last - next).sign
        }
        return value
    }
}


package aoc.utils

import aoc.utils.model.GridPoint2D
import aoc.utils.model.Vec2

/**
 * An iterator over a progression of values of type [GridPoint2D].
 * @property step the number by which the value is incremented on each step.
 */
internal class GridPoint2DProgressionIterator(first: GridPoint2D, last: GridPoint2D, val step: Vec2) : Iterator<GridPoint2D> {
    private val finalElement = last
    private var hasNext: Boolean = !isEmpty(step, first, last)
    private var next: GridPoint2D = if (hasNext) first else finalElement

    override fun hasNext(): Boolean = hasNext

    override fun next(): GridPoint2D {
        val value = next
        if (value == finalElement) {
            if (!hasNext) throw kotlin.NoSuchElementException()
            hasNext = false
        } else {
            next += step
        }
        return value
    }
}

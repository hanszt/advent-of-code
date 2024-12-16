package aoc.utils

import aoc.utils.model.GridPoint2D
import aoc.utils.model.Vec2
import aoc.utils.model.gridPoint2D

/**
 * A progression of values of type [GridPoint2D].
 */
open class GridPoint2DProgression internal constructor(
    start: GridPoint2D,
    endInclusive: GridPoint2D,
    /**
     * The step of the progression.
     */
    val step: Vec2
) : Iterable<GridPoint2D> {
    init {
        val diff = start - endInclusive
        if (step == Vec2.ZERO) throw kotlin.IllegalArgumentException("Step must be non-zero.")
        if (diff.x * diff.y != 0 && diff.x != diff.y) throw kotlin.IllegalArgumentException("Step direction must be horizontal, vertical or diagonal.")
        if (step.x == Int.MIN_VALUE || step.y == Int.MIN_VALUE) throw kotlin.IllegalArgumentException("Step must be greater than Int.MIN_VALUE to avoid overflow on negation.")
    }

    /**
     * The first element in the progression.
     */
    val first = start

    /**
     * The last element in the progression.
     */
    val last = getProgressionLastElement(start, endInclusive, step)

    override fun iterator(): Iterator<GridPoint2D> = GridPoint2DProgressionIterator(first, last, step)

    /**
     * Checks if the progression is empty.
     *
     * Progression with a positive step is empty if its first element is greater than the last element.
     * Progression with a negative step is empty if its first element is less than the last element.
     */
    open fun isEmpty(): Boolean = isEmpty(step, first, last)

    override fun equals(other: Any?): Boolean =
        other is GridPoint2DProgression && (isEmpty() && other.isEmpty() ||
                first == other.first && last == other.last && step == other.step)

    override fun hashCode(): Int =
        if (isEmpty()) {
            -1
        } else {
            var result = first.hashCode()
            result = 31 * result + last.hashCode()
            result = 31 * result + step.hashCode()
            result
        }

    override fun toString(): String = "$first..$last"
}

internal fun getProgressionLastElement(start: GridPoint2D, end: GridPoint2D, step: Vec2): GridPoint2D {
    val x = when {
        step.x > 0 -> if (start.x >= end.x) end.x else end.x - differenceModulo(end.x, start.x, step.x)
        step.x <= 0 -> if (start.x <= end.x) end.x else end.x + differenceModulo(start.x, end.x, -step.x)
        else -> throw kotlin.IllegalArgumentException("Step is zero.")
    }
    val y = when {
        step.y > 0 -> if (start.y >= end.y) end.y else end.y - differenceModulo(end.y, start.y, step.y)
        step.y <= 0 -> if (start.y <= end.y) end.y else end.y + differenceModulo(start.y, end.y, -step.y)
        else -> throw kotlin.IllegalArgumentException("Step is zero.")
    }
    return gridPoint2D(x, y)
}

private fun mod(a: Int, b: Int): Int {
    val mod = a % b
    return if (mod >= 0) mod else mod + b
}

// (a - b) mod c
private fun differenceModulo(a: Int, b: Int, c: Int): Int {
    return mod(mod(a, c) - mod(b, c), c)
}

internal fun isEmpty(step: Vec2, first: GridPoint2D, last: GridPoint2D): Boolean = !(when {
    step.y > 0 && step.x > 0 -> first.x <= last.x && first.y <= last.y
    step.y > 0 && step.x <= 0 -> first.x >= last.x && first.y <= last.y
    step.y <= 0 && step.x > 0 -> first.x <= last.x && first.y >= last.y
    step.y <= 0 && step.x <= 0 -> first.x >= last.x && first.y >= last.y
    else -> error("step is zero")
})

/**
 * Returns a progression that goes over the same range with the given step.
 */
infix fun GridPoint2DProgression.step(step: Vec2): GridPoint2DProgression {
    return GridPoint2DProgression(first, last, step)
}

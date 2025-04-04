package aoc.seastories.model

import aoc.utils.grid2d.GridPoint2D
import kotlin.math.abs

internal data class Line(val begin: GridPoint2D, val end: GridPoint2D) {

    val coordinates: Set<GridPoint2D> = toLineCoordinates(begin, end)

    fun isHorizontal(): Boolean = begin.y == end.y

    fun isVertical(): Boolean = begin.x == end.x

    private fun isDiagonal(): Boolean = abs(begin.x - end.x) == abs(begin.y - end.y)

    private fun toLineCoordinates(begin: GridPoint2D, end: GridPoint2D): Set<GridPoint2D> {
        if (isVertical() or isHorizontal() or isDiagonal()) {
            val dir = (end - begin).sign
            return (0..abs(begin.x - end.x).coerceAtLeast(abs(begin.y - end.y)))
                .map { step -> begin + dir * step }
                .toSet()
        } else throw UnsupportedOperationException("Only horizontal, vertical or diagonal lines supported")
    }

    override fun toString(): String = "Line(begin=$begin, end=$end, coordinates=$coordinates)"
}

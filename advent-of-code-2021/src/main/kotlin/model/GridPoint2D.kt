package model

import kotlin.math.abs
import kotlin.math.sign

internal data class GridPoint2D(val x: Int, val y: Int) {
    operator fun plus(other: GridPoint2D) = GridPoint2D(x + other.x, y + other.y)
    fun plusX(x: Int) = GridPoint2D(this.x + x, y)
    fun plusY(y: Int) = GridPoint2D(x, this.y + y)
    operator fun minus(other: GridPoint2D) = GridPoint2D(x - other.x, y - other.y)
    operator fun times(factor: Int) = GridPoint2D(x * factor, y * factor)
    fun toSignVector() = GridPoint2D(x.sign, y.sign)
    fun gridDistance(other: GridPoint2D) = abs(x - other.x).coerceAtLeast(abs(y - other.y))
}

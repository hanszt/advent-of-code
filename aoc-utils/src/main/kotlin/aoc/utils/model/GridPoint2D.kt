package aoc.utils.model

import kotlin.math.abs
import kotlin.math.sign

interface GridPoint2D {

    val x: Int
    val y: Int

    operator fun component1(): Int = x
    operator fun component2(): Int = y
    operator fun plus(other: GridPoint2D) = x + other.x by y + other.y

    fun add(other: GridPoint2D) = plus(other)
    fun add(x: Int, y: Int) = this.x + x by this.y + y
    fun plusX(x: Int) = this.x + x by y
    fun plusY(y: Int) = x by this.y + y
    operator fun minus(other: GridPoint2D) = x - other.x by y - other.y
    operator fun times(factor: Int) = x * factor by y * factor
    fun toSignVector() = x.sign by y.sign
    fun manhattanDistance(other: GridPoint2D) = abs(x - other.x) + abs(y - other.y)

    companion object {
        @JvmField
        val ZERO = 0 by 0

        @JvmField
        val up = gridPoint2D(0, 1)
        @JvmField
        val down = gridPoint2D(0, -1)
        @JvmField
        val right = gridPoint2D(1, 0)
        @JvmField
        val left = gridPoint2D(-1, 0)

        @JvmField
        val orthoDirs = listOf(up, down, right, left)

        infix fun Int.by(y: Int): GridPoint2D = GridPoint2DImpl(this, y)

        @JvmStatic
        fun of(x: Int, y: Int): GridPoint2D = x by y
    }
}

fun gridPoint2D(x: Int, y: Int) = GridPoint2D.of(x, y)

private data class GridPoint2DImpl(override val x: Int, override val y: Int) : GridPoint2D

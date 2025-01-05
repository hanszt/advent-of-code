package aoc.utils.grid2d

import aoc.utils.grid3d.GridPoint3D
import kotlin.math.abs
import kotlin.math.sign

typealias Vec2 = GridPoint2D

interface GridPoint2D {

    val x: Int
    val y: Int

    val sign get(): Vec2 = x.sign by y.sign

    operator fun component1(): Int = x
    operator fun component2(): Int = y
    operator fun plus(other: GridPoint2D) = x + other.x by y + other.y
    operator fun minus(other: GridPoint2D) = x - other.x by y - other.y
    operator fun times(factor: Int) = x * factor by y * factor
    operator fun div(factor: Int) = x / factor by y / factor
    operator fun unaryMinus() = GridPoint2D(-x, -y)
    fun dotProduct(other: GridPoint2D): Int = x * other.x + y * other.y
    fun crossProduct(other: GridPoint2D): GridPoint3D = GridPoint3D(0, 0, x * other.y - y * other.x)

    fun plus(x: Int, y: Int) = this.x + x by this.y + y
    fun plusX(x: Int) = this.x + x by y
    fun plusY(y: Int) = x by this.y + y
    fun rot90L(): GridPoint2D = GridPoint2D(y, -x)
    fun rot90R(): GridPoint2D = GridPoint2D(-y, x)
    fun manhattanDistance(other: GridPoint2D) = abs(x - other.x) + abs(y - other.y)

    companion object {
        @JvmField
        val ZERO = 0 by 0

        @JvmField
        val down = GridPoint2D(0, 1)

        @JvmField
        val up = GridPoint2D(0, -1)

        @JvmField
        val right = GridPoint2D(1, 0)

        @JvmField
        val left = GridPoint2D(-1, 0)

        @JvmField
        val north = GridPoint2D(0, -1)

        @JvmField
        val northeast = GridPoint2D(1, -1)

        @JvmField
        val east = GridPoint2D(1, 0)

        @JvmField
        val southeast = GridPoint2D(1, 1)

        @JvmField
        val south = GridPoint2D(0, 1)

        @JvmField
        val southwest = GridPoint2D(-1, 1)

        @JvmField
        val west = GridPoint2D(-1, 0)

        @JvmField
        val northwest = GridPoint2D(-1, -1)

        /**
         * Same value order as the mathematical unit circle travel order
         */
        @JvmField
        val kingDirs = listOf(east, southeast, south, southwest, west, northwest, north, northeast)

        @JvmField
        val rookDirs = listOf(southeast, southwest, northwest, northeast)

        /**
         * RDLU dirs
         */
        @JvmField
        val towerDirs = listOf(right, down, left, up)

        @JvmField
        val orthoDirs = towerDirs

        infix fun Int.by(y: Int): GridPoint2D = GridPoint2D(this, y)

        @JvmStatic
        fun of(x: Int, y: Int): GridPoint2D = GridPoint2D(x, y)
    }
}

fun GridPoint2D(x: Int, y: Int): GridPoint2D = StandardGridPoint2D(x, y)

private data class StandardGridPoint2D(override val x: Int, override val y: Int) : GridPoint2D {
    override fun toString(): String = "GridPoint2D(x=$x, y=$y)"
}

package aoc.utils.grid2d

import aoc.utils.grid3d.GridPoint3D
import aoc.utils.grid3d.gridPoint3D
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
    operator fun unaryMinus() = gridPoint2D(-x, -y)
    fun dotProduct(other: GridPoint2D): Int = x * other.x + y * other.y
    fun crossProduct(other: GridPoint2D): GridPoint3D = gridPoint3D(0, 0, x * other.y - y * other.x)

    fun plus(x: Int, y: Int) = this.x + x by this.y + y
    fun plusX(x: Int) = this.x + x by y
    fun plusY(y: Int) = x by this.y + y
    fun rot90L(): GridPoint2D = gridPoint2D(y, -x)
    fun rot90R(): GridPoint2D = gridPoint2D(-y, x)
    fun manhattanDistance(other: GridPoint2D) = abs(x - other.x) + abs(y - other.y)

    companion object {
        @JvmField
        val ZERO = 0 by 0

        @JvmField
        val down = gridPoint2D(0, 1)

        @JvmField
        val up = gridPoint2D(0, -1)

        @JvmField
        val right = gridPoint2D(1, 0)

        @JvmField
        val left = gridPoint2D(-1, 0)

        @JvmField
        val north = gridPoint2D(0, -1)

        @JvmField
        val northeast = gridPoint2D(1, -1)

        @JvmField
        val east = gridPoint2D(1, 0)

        @JvmField
        val southeast = gridPoint2D(1, 1)

        @JvmField
        val south = gridPoint2D(0, 1)

        @JvmField
        val southwest = gridPoint2D(-1, 1)

        @JvmField
        val west = gridPoint2D(-1, 0)

        @JvmField
        val northwest = gridPoint2D(-1, -1)

        /**
         * Same value order as the mathematical unit circle travel order
         */
        @JvmField
        val kingDirs = listOf(east, southeast, south, southwest, west, northwest, north, northeast)

        @JvmField
        val rookDirs = listOf(southeast, southwest, northwest, northeast)

        @JvmField
        val towerDirs = listOf(right, down, left, up)

        @JvmField
        val orthoDirs = towerDirs

        infix fun Int.by(y: Int): GridPoint2D = StandardGridPoint2D(this, y)

        @JvmStatic
        fun of(x: Int, y: Int): GridPoint2D = x by y
    }
}

/**
 * A function to make sure the coordinate jumps to the other side of the grid when over the edge.
 */
fun GridPoint2D.mod(width: Int, height: Int): GridPoint2D = gridPoint2D(x.mod(width), y.mod(height))
fun GridPoint2D.mod(dimension2D: Dimension2D): GridPoint2D = gridPoint2D(x.mod(dimension2D.width), y.mod(dimension2D.height))
fun gridPoint2D(x: Int, y: Int) = GridPoint2D.of(x, y)

private data class StandardGridPoint2D(override val x: Int, override val y: Int) : GridPoint2D {
    override fun toString(): String = "GridPoint2D(x=$x, y=$y)"
}

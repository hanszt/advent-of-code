package aoc.utils.grid3d

import aoc.utils.grid2d.GridPoint2D
import kotlin.math.abs
import kotlin.math.sign

typealias Vec3 = GridPoint3D

interface GridPoint3D {

    val x: Int
    val y: Int
    val z: Int

    val sign get(): Vec3 = gridPoint3D(x.sign, y.sign, z.sign)

    operator fun component1(): Int = x
    operator fun component2(): Int = y
    operator fun component3(): Int = z
    operator fun minus(other: GridPoint3D) = gridPoint3D(x - other.x, y - other.y, z - other.z)
    operator fun plus(other: GridPoint3D) = gridPoint3D(x + other.x, y + other.y, z + other.z)
    operator fun times(factor: Int) = gridPoint3D(x * factor, y * factor, z * factor)
    operator fun unaryMinus() = gridPoint3D(-x, -y, -z)
    fun dotProduct(other: GridPoint3D): Int = x * other.x + y * other.y + z * other.z
    fun manhattanDistance(other: GridPoint3D) = abs(x - other.x) + abs(y - other.y) + abs(z - other.z)

    companion object {
        @JvmField
        val ZERO = of(0, 0, 0)

        @JvmField
        val orthoDirs = listOf(
            gridPoint3D(0, 0, 1),
            gridPoint3D(0, 0, -1),
            gridPoint3D(0, 1, 0),
            gridPoint3D(0, -1, 0),
            gridPoint3D(1, 0, 0),
            gridPoint3D(-1, 0, 0)
        )

        infix fun GridPoint2D.by(z: Int) = of(x, y, z)

        @JvmStatic
        fun of(x: Int, y: Int, z: Int): GridPoint3D = StandardGridPoint3D(x, y, z)
    }
}

fun gridPoint3D(x: Int, y: Int, z: Int) = GridPoint3D.of(x, y, z)

private data class StandardGridPoint3D(override val x: Int, override val y: Int, override val z: Int) : GridPoint3D {
    override fun toString(): String = "GridPoint3D(x=$x, y=$y, z=$z)"
}

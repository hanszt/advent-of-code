package aoc.utils.grid3d

import aoc.utils.grid2d.GridPoint2D
import kotlin.math.abs
import kotlin.math.sign

typealias Vec3 = GridPoint3D

interface GridPoint3D {

    val x: Int
    val y: Int
    val z: Int

    val sign get(): Vec3 = GridPoint3D(x.sign, y.sign, z.sign)

    operator fun component1(): Int = x
    operator fun component2(): Int = y
    operator fun component3(): Int = z
    operator fun minus(other: GridPoint3D) = GridPoint3D(x - other.x, y - other.y, z - other.z)
    operator fun plus(other: GridPoint3D) = GridPoint3D(x + other.x, y + other.y, z + other.z)
    operator fun times(factor: Int) = GridPoint3D(x * factor, y * factor, z * factor)
    operator fun unaryMinus() = GridPoint3D(-x, -y, -z)
    fun dotProduct(other: GridPoint3D): Int = x * other.x + y * other.y + z * other.z
    fun manhattanDistance(other: GridPoint3D) = abs(x - other.x) + abs(y - other.y) + abs(z - other.z)
    fun distanceSquared(other: GridPoint3D): Long {
        val dx = (x - other.x).toLong()
        val dy = (y - other.y).toLong()
        val dz = (z - other.z).toLong()
        return dx * dx + dy * dy + dz * dz
    }

    companion object {
        @JvmField
        val ZERO = of(0, 0, 0)

        @JvmField
        val orthoDirs = listOf(
            GridPoint3D(0, 0, 1),
            GridPoint3D(0, 0, -1),
            GridPoint3D(0, 1, 0),
            GridPoint3D(0, -1, 0),
            GridPoint3D(1, 0, 0),
            GridPoint3D(-1, 0, 0)
        )

        infix fun GridPoint2D.by(z: Int) = of(x, y, z)

        @JvmStatic
        fun of(x: Int, y: Int, z: Int): GridPoint3D = StandardGridPoint3D(x, y, z)
    }
}

fun GridPoint3D(x: Int, y: Int, z: Int) = GridPoint3D.of(x, y, z)

private data class StandardGridPoint3D(override val x: Int, override val y: Int, override val z: Int) : GridPoint3D {
    override fun toString(): String = "GridPoint3D(x=$x, y=$y, z=$z)"
}

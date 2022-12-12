package aoc.utils.model

import kotlin.math.abs

interface GridPoint3D {

    val x: Int
    val y: Int
    val z: Int

    operator fun component1(): Int = x
    operator fun component2(): Int = y
    operator fun component3(): Int = z
    operator fun minus(other: GridPoint3D) = gridPoint3D(x - other.x, y - other.y, z - other.z)
    operator fun plus(other: GridPoint3D) = gridPoint3D(x + other.x, y + other.y, z + other.z)
    fun gridDistance(other: GridPoint3D) = abs(x - other.x) + abs(y - other.y) + abs(z - other.z)

    companion object {
        @JvmField
        val ZERO = of(0, 0, 0)

        infix fun GridPoint2D.by(z: Int) = of(x, y, z)

        @JvmStatic
        fun of(x: Int, y: Int, z: Int): GridPoint3D = GridPoint3DImpl(x, y, z)
    }
}

fun gridPoint3D(x: Int, y: Int, z: Int) = GridPoint3D.of(x, y, z)

private data class GridPoint3DImpl(override val x: Int, override val y: Int, override val z: Int) : GridPoint3D

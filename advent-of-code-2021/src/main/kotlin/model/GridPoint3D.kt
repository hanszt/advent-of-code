package model

import kotlin.math.abs

data class GridPoint3D(val x: Int, val y: Int, val z: Int) {

    operator fun minus(other: GridPoint3D) = GridPoint3D(x - other.x, y - other.y, z - other.z)
    operator fun plus(other: GridPoint3D) = GridPoint3D(x + other.x, y + other.y, z + other.z)
    fun distance(other: GridPoint3D) = abs(x - other.x) + abs(y - other.y) + abs(z - other.z)

    companion object {
        val zero = GridPoint3D(0, 0, 0)
    }
}

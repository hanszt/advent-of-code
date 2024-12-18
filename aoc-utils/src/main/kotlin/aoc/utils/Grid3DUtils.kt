package aoc.utils

import aoc.utils.model.GridPoint3D
import aoc.utils.model.gridPoint3D as point3D

class Transform3D(val rotation: (GridPoint3D) -> GridPoint3D, val translation: GridPoint3D)

fun GridPoint3D.transform(transform: Transform3D): GridPoint3D = transform.rotation(this) + transform.translation

fun GridPoint3D.transform(transforms: List<Transform3D>): GridPoint3D = transforms.fold(this, GridPoint3D::transform)

/**
 * All 24 different 90 deg rotations a 3d object can make.
 */
val rotations = listOf<(GridPoint3D) -> GridPoint3D>(
    { (x, y, z) -> point3D(x, y, z) },
    { (x, y, z) -> point3D(y, z, x) },
    { (x, y, z) -> point3D(z, x, y) },

    { (x, y, z) -> point3D(-x, y, -z) },
    { (x, y, z) -> point3D(-y, z, -x) },
    { (x, y, z) -> point3D(-z, x, -y) },

    { (x, y, z) -> point3D(x, z, -y) },
    { (x, y, z) -> point3D(y, x, -z) },
    { (x, y, z) -> point3D(z, y, -x) },

    { (x, y, z) -> point3D(-x, z, y) },
    { (x, y, z) -> point3D(-y, x, z) },
    { (x, y, z) -> point3D(-z, y, x) },

    { (x, y, z) -> point3D(x, -y, -z) },
    { (x, y, z) -> point3D(y, -z, -x) },
    { (x, y, z) -> point3D(z, -x, -y) },

    { (x, y, z) -> point3D(-x, -y, z) },
    { (x, y, z) -> point3D(-y, -z, x) },
    { (x, y, z) -> point3D(-z, -x, y) },

    { (x, y, z) -> point3D(x, -z, y) },
    { (x, y, z) -> point3D(y, -x, z) },
    { (x, y, z) -> point3D(z, -y, x) },

    { (x, y, z) -> point3D(-x, -z, -y) },
    { (x, y, z) -> point3D(-y, -x, -z) },
    { (x, y, z) -> point3D(-z, -y, -x) }
)

/**
 * Ranges
 */
operator fun GridPoint3D.rangeTo(other: GridPoint3D): GridPoint3DRange = GridPoint3DRange(
    start = this,
    endInclusive = other,
)

interface ClosedGridPoint3DRange {
    val start: GridPoint3D
    val endInclusive: GridPoint3D

    operator fun contains(point: GridPoint3D): Boolean =
        point.x in start.x..endInclusive.x
                && point.y in start.y..endInclusive.y
                && point.z in start.z..endInclusive.z
}

class GridPoint3DRange internal constructor(
    override val start: GridPoint3D,
    override val endInclusive: GridPoint3D
) : GridPoint3DProgression(start, endInclusive), ClosedGridPoint3DRange

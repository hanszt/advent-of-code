package aoc.utils

import aoc.utils.model.GridPoint3D
import aoc.utils.model.gridPoint3D as point3D

class Transform3D(val dir: Int, val translation: GridPoint3D)

fun GridPoint3D.transform(transform: Transform3D): GridPoint3D = rotated(transform.dir) + transform.translation

fun GridPoint3D.transform(transforms: List<Transform3D>): GridPoint3D = transforms.fold(this, GridPoint3D::transform)

fun GridPoint3D.rotated(orientation: Int): GridPoint3D = orientations[orientation](this)

val orientations = listOf<(GridPoint3D) -> GridPoint3D>(
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

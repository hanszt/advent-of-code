package utils

import model.GridPoint3D

class Transform3D(val dir: Int, val translation: GridPoint3D)

fun GridPoint3D.transform(transform: Transform3D): GridPoint3D = rotate(transform.dir) + transform.translation

fun GridPoint3D.transform(transforms: List<Transform3D>): GridPoint3D = transforms.fold(this, GridPoint3D::transform)

fun GridPoint3D.rotate(orientation: Int): GridPoint3D = orientations[orientation].invoke(this)

val orientations = listOf<(GridPoint3D) -> GridPoint3D>(
    { (x, y, z) -> GridPoint3D(x, y, z) },
    { (x, y, z) -> GridPoint3D(y, z, x) },
    { (x, y, z) -> GridPoint3D(z, x, y) },

    { (x, y, z) -> GridPoint3D(-x, y, -z) },
    { (x, y, z) -> GridPoint3D(-y, z, -x) },
    { (x, y, z) -> GridPoint3D(-z, x, -y) },

    { (x, y, z) -> GridPoint3D(x, z, -y) },
    { (x, y, z) -> GridPoint3D(y, x, -z) },
    { (x, y, z) -> GridPoint3D(z, y, -x) },

    { (x, y, z) -> GridPoint3D(-x, z, y) },
    { (x, y, z) -> GridPoint3D(-y, x, z) },
    { (x, y, z) -> GridPoint3D(-z, y, x) },

    { (x, y, z) -> GridPoint3D(x, -y, -z) },
    { (x, y, z) -> GridPoint3D(y, -z, -x) },
    { (x, y, z) -> GridPoint3D(z, -x, -y) },

    { (x, y, z) -> GridPoint3D(-x, -y, z) },
    { (x, y, z) -> GridPoint3D(-y, -z, x) },
    { (x, y, z) -> GridPoint3D(-z, -x, y) },

    { (x, y, z) -> GridPoint3D(x, -z, y) },
    { (x, y, z) -> GridPoint3D(y, -x, z) },
    { (x, y, z) -> GridPoint3D(z, -y, x) },

    { (x, y, z) -> GridPoint3D(-x, -z, -y) },
    { (x, y, z) -> GridPoint3D(-y, -x, -z) },
    { (x, y, z) -> GridPoint3D(-z, -y, -x) }
)

package aoc.utils.model

import aoc.utils.OpenEndedGridPoint2DRange

interface Dimension2D : OpenEndedGridPoint2DRange, Iterable<GridPoint2D> {
    val width: Int
    val height: Int

    operator fun component1() = width
    operator fun component2() = height

    val surfaceArea: Int get() = width * height

    override fun iterator(): Iterator<GridPoint2D> = object : Iterator<GridPoint2D> {

        var x = 0
        var y = 0

        override fun hasNext(): Boolean = x < width && y < height

        override fun next(): GridPoint2D {
            if (hasNext()) {
                val gridPoint2D = gridPoint2D(x, y)
                if (x >= width - 1) {
                    x = 0
                    y++
                } else x++
                return gridPoint2D
            }
            throw NoSuchElementException()
        }

    }
}

fun dimension2D(width: Int, height: Int): Dimension2D = StandardDimension2D(width, height)

private data class StandardDimension2D(override val width: Int, override val height: Int) : Dimension2D {
    override val start: GridPoint2D = gridPoint2D(0, 0)
    override val endExclusive: GridPoint2D = gridPoint2D(width - 1, height - 1)

    init {
        require(width >= 0)
        require(height >= 0)
    }
}

package aoc.utils.model

import aoc.utils.ClosedGridPoint2DRange
import aoc.utils.OpenEndedGridPoint2DRange

interface Dimension2D : ClosedGridPoint2DRange, Iterable<GridPoint2D> {
    val width: Int
    val height: Int

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

    fun toOpenEndedRange(): OpenEndedGridPoint2DRange = object : OpenEndedGridPoint2DRange {
        override val start: GridPoint2D = gridPoint2D(0, 0)
        override val endExclusive: GridPoint2D = gridPoint2D(width - 1, height - 1)
    }
}

fun dimension2D(width: Int, height: Int): Dimension2D = StandardDimension2D(width, height)

private data class StandardDimension2D(override val width: Int, override val height: Int) : Dimension2D {
    override val start: GridPoint2D = gridPoint2D(0, 0)
    override val endInclusive: GridPoint2D = gridPoint2D(width, height)

    init {
        require(width >= 0)
        require(height >= 0)
    }
}

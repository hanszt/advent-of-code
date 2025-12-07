package aoc.utils.grid2d

interface Dimension2D : OpenEndedGridPoint2DRange {
    val width: Int
    val height: Int

    override val start: GridPoint2D get() = GridPoint2D.ZERO
    override val endExclusive: GridPoint2D get() = GridPoint2D(width - 1, height - 1)

    operator fun component1() = width
    operator fun component2() = height

    val surfaceArea: Int get() = width * height

    fun gridPoints(): Sequence<GridPoint2D> = Sequence {
        object : Iterator<GridPoint2D> {

            var x = 0
            var y = 0

            override fun hasNext(): Boolean = x < width && y < height

            override fun next(): GridPoint2D {
                if (hasNext()) {
                    val gridPoint2D = GridPoint2D(x, y)
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
}

fun dimension2D(width: Int, height: Int): Dimension2D = StandardDimension2D(width, height)

private data class StandardDimension2D(override val width: Int, override val height: Int) : Dimension2D {
    override val endExclusive: GridPoint2D = GridPoint2D(width - 1, height - 1)

    init {
        require(width >= 0)
        require(height >= 0)
    }

    override fun toString(): String = "(width=$width, height=$height)"
}

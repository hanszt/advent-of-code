package aoc.seastories

import aoc.utils.model.GridPoint2D
import aoc.utils.model.GridPoint2D.Companion.by
import aoc.utils.toIntGrid
import java.io.File

internal class Day09SmokeBasin(inputPath: String) : ChallengeDay {

    private val grid = File(inputPath).readLines().toIntGrid(Char::digitToInt)

    override fun part1() = grid.toLowPoints().sumOf { (_, height) -> height + 1 }
    override fun part2() = grid.findBassinSizes()
        .sortedDescending()
        .slice(0 ..< 3)
        .reduce { thisSize, otherSize -> thisSize * otherSize }

    private fun Array<IntArray>.findBassinSizes() =
        toLowPoints().map { (lowPoint) ->
            val basinPoints = mutableSetOf(lowPoint)
            findBassinPoints(lowPoint.x, lowPoint.y, basinPoints)
            return@map basinPoints.size
        }

    companion object {

        fun Array<IntArray>.findBassinPoints(x: Int, y: Int, bassinPoints: MutableSet<GridPoint2D>) {
            val basinSize = bassinPoints.size
            for ((dx, dy) in GridPoint2D.orthoDirs) {
                val xNew = x + dx
                val yNew = y + dy
                val neighborHeight = getOrNull(yNew)?.getOrNull(xNew) ?: continue
                if (neighborHeight != 9 && neighborHeight > this[y][x]) {
                    bassinPoints.add(xNew by yNew)
                    findBassinPoints(xNew, yNew, bassinPoints)
                }
            }
            if (basinSize == bassinPoints.size) return
        }

        fun Array<IntArray>.toLowPoints() = heightToNeighborHeights()
            .filter { (_, height, neighborHeights) -> neighborHeights.all { it > height } }

        private fun Array<IntArray>.heightToNeighborHeights() =
            indices.flatMap { y ->
                first().indices.map { x ->
                    val neighborHeights = GridPoint2D.orthoDirs.mapNotNull { (dx, dy) -> getOrNull(y + dy)?.getOrNull(x + dx) }
                    return@map Triple(x by y, this[y][x], neighborHeights)
                }
            }
    }
}

package aoc.seastories

import aoc.seastories.model.Line
import aoc.utils.model.GridPoint2D.Companion.by
import java.io.File
import kotlin.math.max

internal class Day05HydrothermalVenture(filePath: String) : ChallengeDay {

    private val lines = File(filePath).useLines { it.toVentureLines() }

    override fun part1(): Int = lines
        .filter { it.isHorizontal() or it.isVertical() }.asGrid()
        .countIntersections()

    override fun part2(): Int = lines.asGrid().countIntersections()

    companion object {
        fun Sequence<String>.toVentureLines() = map(::toBeginAndEndPoint).map { (begin, end) -> Line(begin, end) }.toSet()
        private fun toBeginAndEndPoint(line: String) = line.split("->").map(String::trim).map(::toGridPoint)
        private fun toGridPoint(s: String) = s.split(',').map(String::toInt).let { (x, y) -> x by y }

        fun Iterable<Line>.asGrid(): Array<IntArray> {
            val nrOfRows = maxOf { (begin, end) -> max(begin.y, end.y) } + 1
            val nrOfCols = maxOf { (begin, end) -> max(begin.x, end.x) } + 1
            val grid: Array<IntArray> = Array(nrOfRows) { IntArray(nrOfCols) }
            flatMap(Line::coordinates).forEach { (col, row) -> grid[row][col]++ }
            return grid
        }

        fun Array<IntArray>.countIntersections(): Int = asSequence()
            .flatMap(IntArray::asSequence)
            .filter { it > 1 }
            .count()
    }
}

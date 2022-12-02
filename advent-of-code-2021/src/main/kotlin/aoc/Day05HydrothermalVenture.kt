package aoc

import model.GridPoint2D
import model.Line
import java.io.File
import kotlin.math.max

internal object Day05HydrothermalVenture : ChallengeDay {

    fun part1(path: String): Int = File(path).toVentureLines()
        .filter { it.isHorizontal() or it.isVertical() }.asGrid()
        .countIntersections()

    fun Array<IntArray>.countIntersections(): Int = asSequence()
        .flatMap(IntArray::asSequence)
        .filter { it > 1 }
        .count()

    fun File.toVentureLines(): Set<Line> =
        useLines { it.map(::toBeginAndEndPoint).map { (begin, end) -> Line(begin, end) }.toSet() }

    private fun toBeginAndEndPoint(line: String) = line.split("->").map(String::trim).map(::toGridPoint)

    private fun toGridPoint(s: String) = s.split(',').map(String::toInt).let { (x, y) -> GridPoint2D(x, y) }

    fun part2(path: String): Int = File(path).toVentureLines().asGrid().countIntersections()

    fun Iterable<Line>.asGrid(): Array<IntArray> {
        val nrOfRows = maxOf { (begin, end) -> max(begin.y, end.y) } + 1
        val nrOfCols = maxOf { (begin, end) -> max(begin.x, end.x) } + 1
        val grid: Array<IntArray> = Array(nrOfRows) { IntArray(nrOfCols) }
        flatMap(Line::coordinates).forEach { (col, row) -> grid[row][col]++ }
        return grid
    }

    override fun part1() = part1(ChallengeDay.inputDir + "/day5.txt")
    override fun part2() = part2(ChallengeDay.inputDir + "/day5.txt")
}

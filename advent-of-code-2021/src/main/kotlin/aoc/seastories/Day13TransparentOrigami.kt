package aoc.seastories

import aoc.utils.*
import aoc.utils.grid2d.gridAsString
import aoc.utils.grid2d.mapByPoint
import aoc.utils.grid2d.mirroredVertically
import aoc.utils.grid2d.rotated
import aoc.utils.grid2d.rotatedCc
import java.io.File

internal class Day13TransparentOrigami(private val inputPath: String) : ChallengeDay {

    override fun part1(): Int {
        val (coordinates, foldInstructions) = getCoordinatesAndFoldInstructions(inputPath)
        return toGrid(coordinates, foldInstructions)
            .foldGrid(foldInstructions.first())
            .flatMap(CharArray::toList)
            .count { it == BLOCK }
    }

    override fun part2() = part2GridAsString().toExpectedTextOrElseThrow()

    private fun getCoordinatesAndFoldInstructions(path: String): Pair<String, List<Pair<Char, Int>>> {
        val (coordinatesAsString, foldInstr) = File(path).readText().splitByBlankLine()
        val foldInstructions = foldInstr.lines().map { it.split('=') }
            .map { (dir, value) -> dir.last() to value.toInt() }
        return coordinatesAsString to foldInstructions
    }

    private fun Array<CharArray>.foldGrid(foldInstr: Pair<Char, Int>): Array<CharArray> {
        val (dir, value) = foldInstr
        return when (dir) {
            'x' -> foldAlongX(value)
            'y' -> foldAlongY(value)
            else -> error("unsupported dir: $dir")
        }
    }

    private fun Array<CharArray>.foldAlongY(value: Int) = let { grid ->
        val top = grid.sliceArray(0 until value)
        val mirroredBottom = grid.sliceArray(value + 1 until grid.size).mirroredVertically()
        return@let top.mapByPoint { x, y -> if (top[y][x] == '.') mirroredBottom[y][x] else '█' }
    }

    private fun Array<CharArray>.foldAlongX(value: Int) = rotated().foldAlongY(value).rotatedCc()

    private fun toGrid(coordinates: String, foldInstrList: List<Pair<Char, Int>>): Array<CharArray> {
        val firstXFold = foldInstrList.first { (dir) -> dir == 'x' }.second
        val firstYFold = foldInstrList.first { (dir) -> dir == 'y' }.second
        val points = coordinates.trim().lines()
            .map { it.split(',').map(String::toInt) }
            .map { (x, y) -> x to y }
        val grid = Array(firstYFold * 2 + 1) { CharArray(firstXFold * 2 + 1) { '.' } }
        points.forEach { (x, y) -> grid[y][x] = BLOCK }
        return grid
    }

    fun part2GridAsString(): String {
        val (coordinates, foldInstructions) = getCoordinatesAndFoldInstructions(inputPath)
        return toGrid(coordinates, foldInstructions)
            .foldByInstructions(foldInstructions)
            .gridAsString(1)
    }

    private fun Array<CharArray>.foldByInstructions(instructions: List<Pair<Char, Int>>): Array<CharArray> =
        instructions.fold(this) { grid, instruction -> grid.foldGrid(instruction) }

    companion object {
        //later added to display result as text when launched from aoc.main
        internal fun String.toExpectedTextOrElseThrow(): String {
            val expected = """
            .██..███..████.█....███..████.████.█....
            █..█.█..█....█.█....█..█.█.......█.█....
            █....█..█...█..█....█..█.███....█..█....
            █....███...█...█....███..█.....█...█....
            █..█.█....█....█....█....█....█....█....
            .██..█....████.████.█....█....████.████.
        """.trimIndent().trim()
            val text = "CPZLPFZL"
            if (this == expected) return text else {
                val cause = "The expected grid for $text is: %n%n$expected%n%n but found %n%n${this}%n%n".format()
                throw IllegalArgumentException("No matching text found", IllegalArgumentException(cause))
            }
        }
    }

}

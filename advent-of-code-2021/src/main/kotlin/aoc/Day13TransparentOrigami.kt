package aoc

import utils.*
import java.io.File

internal object Day13TransparentOrigami : ChallengeDay {

    fun part1(path: String): Int {
        val (coordinates, foldInstructions) = getCoordinatesAndFoldInstructions(path)
        return toGrid(coordinates, foldInstructions)
            .foldGrid(foldInstructions.first())
            .flatMap(Array<Char>::toList)
            .count { it == '█' }
    }

    private fun getCoordinatesAndFoldInstructions(path: String): Pair<String, List<Pair<Char, Int>>> {
        val (coordinatesAsString, foldInstr) = File(path).readText().splitByBlankLine()
        val foldInstructions = foldInstr.lines().map { it.split('=') }
            .map { (dir, value) -> dir.last() to value.toInt() }
        return coordinatesAsString to foldInstructions
    }

    private fun Array<Array<Char>>.foldGrid(foldInstr: Pair<Char, Int>): Array<Array<Char>> {
        val (dir, value) = foldInstr
        return when (dir) {
            'x' -> foldAlongX(value)
            'y' -> foldAlongY(value)
            else -> throw IllegalStateException()
        }
    }

    private fun Array<Array<Char>>.foldAlongY(value: Int) = let { grid ->
        val top = grid.sliceArray(0 until value)
        val mirroredBottom = grid.sliceArray(value + 1 until grid.size).mirroredVertically()
        return@let top.mapByPoint { x, y -> if (top[y][x] == '.') mirroredBottom[y][x] else '█' }
    }

    private fun Array<Array<Char>>.foldAlongX(value: Int) = rotated().foldAlongY(value).rotatedCc()

    private fun toGrid(coordinates: String, foldInstrList: List<Pair<Char, Int>>): Array<Array<Char>> {
        val firstXFold = foldInstrList.first { (dir) -> dir == 'x' }.second
        val firstYFold = foldInstrList.first { (dir) -> dir == 'y' }.second
        val points = coordinates.trim().lines()
            .map { it.split(',').map(String::toInt) }
            .map { (x, y) -> x to y }
        val grid = Array(firstYFold * 2 + 1) { Array(firstXFold * 2 + 1) { '.' } }
        points.forEach { (x, y) -> grid[y][x] = '█' }
        return grid
    }

    fun part2GridAsString(path: String): String {
        val (coordinates, foldInstructions) = getCoordinatesAndFoldInstructions(path)
        return toGrid(coordinates, foldInstructions)
            .foldByInstructions(foldInstructions)
            .gridAsString(1)
    }

    fun part2(path: String) = part2GridAsString(path).toExpectedTextOrElseThrow()

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

    private fun Array<Array<Char>>.foldByInstructions(instructions: List<Pair<Char, Int>>): Array<Array<Char>> =
        instructions.fold(this) { grid, instruction -> grid.foldGrid(instruction)}

    override fun part1() = part1(ChallengeDay.inputDir + "/day13.txt")
    override fun part2() = part2(ChallengeDay.inputDir + "/day13.txt")
}

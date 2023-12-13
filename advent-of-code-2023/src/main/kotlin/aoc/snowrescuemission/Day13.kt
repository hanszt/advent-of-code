package aoc.snowrescuemission

import aoc.utils.ChallengeDay
import aoc.utils.gridAsString
import java.io.File

class Day13(
    fileName: String? = null,
    text: String = File(fileName ?: error("No fileName or text provided")).readText()
) : ChallengeDay {

    private val patterns = text.splitToSequence("\n\n").map { Pattern(it.lines()) }

    override fun part1(): Int = patterns.sumOf {
        val line = it.findReflectionLine()
        println("line = ${line}")
        when (line) {
            is HorReflectionLine -> line.yBefore * 100
            is VerReflectionLine -> line.xBefore
        }
    }

    override fun part2(): Int = patterns.sumOf {
        val line = it
            .fixSmudge()
            .findReflectionLine()
        println("line = ${line}")
        when (line) {
            is HorReflectionLine -> line.yBefore * 100
            is VerReflectionLine -> line.xBefore
        }
    }

    private data class Pattern(val grid: List<String>) {

        fun findReflectionLine(withSmudge: Boolean = false): ReflectionLine =
            findHorReflection(withSmudge)
                ?: findVerReflection(withSmudge)
                ?: error("No reflection line found in ${grid.gridAsString()}")

        fun findHorReflection(withSmudge: Boolean = false): HorReflectionLine? {
            for (y in 1..<grid.size) {
                val r = grid[y]
                var yAbove = y - 1
                var yBelow = y
                var isHorReflectionLine = true
                findReflection@ while (yAbove >= 0 && yBelow < grid.size) {
                    for (x in r.indices) {
                        if (grid[yAbove][x] != grid[yBelow][x]) {
                            isHorReflectionLine = false
                            break@findReflection
                        }
                    }
                    yAbove--
                    yBelow++
                }
                if (isHorReflectionLine) {
                    return HorReflectionLine(y, y + 1)
                }
            }
            return null
        }

        fun findVerReflection(withSmudge: Boolean = false): VerReflectionLine? {
            for (x in 1..<grid[0].length) {
                var xLeft = x - 1
                var xRight = x
                var isVertReflectionLine = true
                findReflection@ while (xLeft >= 0 && xRight < grid[0].length) {
                    for (y in grid.indices) {
                        if (grid[y][xLeft] != grid[y][xRight]) {
                            isVertReflectionLine = false
                            break@findReflection
                        }
                    }
                    xLeft--
                    xRight++
                }
                if (isVertReflectionLine) {
                    return VerReflectionLine(x, x + 1)
                }
            }
            return null
        }

        fun fixSmudge(): Pattern {
            TODO()
        }

    }

    private sealed interface ReflectionLine
    private data class HorReflectionLine(val yBefore: Int, val yAfter: Int) : ReflectionLine
    private data class VerReflectionLine(val xBefore: Int, val xAfter: Int) : ReflectionLine


}

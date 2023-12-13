package aoc.snowrescuemission

import aoc.utils.ChallengeDay
import aoc.utils.gridAsString
import java.io.File

class Day13(
    fileName: String? = null,
    text: String = File(fileName ?: error("No fileName or text provided")).readText()
) : ChallengeDay {

    private val patterns = text.splitToSequence("\n\n").map { Pattern(it.lines()) }

    override fun part1(): Int = patterns.map(Pattern::findReflectionLine).sumOf(::toSummary)
    override fun part2(): Int = patterns.map(Pattern::findReflectionLineWithSmudge).sumOf(::toSummary)

    private fun toSummary(it: ReflectionLine) = when (it) {
        is HorReflectionLine -> it.yBefore * 100
        is VerReflectionLine -> it.xBefore
    }

    private data class Pattern(val grid: List<String>) {

        private val mutableGrid = grid.map { it.toCharArray() }.toTypedArray()

        fun findReflectionLine(): ReflectionLine =
            findHorReflection()
                ?: findVerReflection()
                ?: error("No reflection line found in ${grid.gridAsString()}")

        fun findReflectionLineWithSmudge(): ReflectionLine =
            findHorReflectionWithSmudge()
                ?: findVerReflectionWithSmudge()
                ?: error("No reflection line found in ${grid.gridAsString()}")

        fun findHorReflection(): HorReflectionLine? {
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

        fun findVerReflection(): VerReflectionLine? {
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

        fun findHorReflectionWithSmudge(): HorReflectionLine? {
            for (y in 1..<grid.size) {
                val r = grid[y]
                var yAbove = y - 1
                var yBelow = y
                var smudgeCount = 0
                findReflection@ while (yAbove >= 0 && yBelow < grid.size) {
                    for (x in r.indices) {
                        if (mutableGrid[yAbove][x] != mutableGrid[yBelow][x]) {
                            if (smudgeCount > 1) {
                                smudgeCount = 0
                                break@findReflection
                            }
                            smudgeCount++
                        }
                    }
                    yAbove--
                    yBelow++
                }
                if (smudgeCount == 1) {
                    return HorReflectionLine(y, y + 1)
                }
            }
            return null
        }

        fun findVerReflectionWithSmudge(): VerReflectionLine? {
            for (x in 1..<grid[0].length) {
                var xLeft = x - 1
                var xRight = x
                var smudgeCount = 0
                findReflection@ while (xLeft >= 0 && xRight < grid[0].length) {
                    for (y in grid.indices) {
                        if (grid[y][xLeft] != grid[y][xRight]) {
                            if (smudgeCount > 1) {
                                smudgeCount = 0
                                break@findReflection
                            }
                            smudgeCount++
                        }
                    }
                    xLeft--
                    xRight++
                }
                if (smudgeCount == 1) {
                    return VerReflectionLine(x, x + 1)
                }
            }
            return null
        }

    }

    private sealed interface ReflectionLine
    private data class HorReflectionLine(val yBefore: Int, val yAfter: Int) : ReflectionLine
    private data class VerReflectionLine(val xBefore: Int, val xAfter: Int) : ReflectionLine


}

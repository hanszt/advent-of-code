package aoc.snowrescuemission

import aoc.utils.AocUtils
import aoc.utils.ChallengeDay
import aoc.utils.grid2d.gridAsString
import aoc.utils.grid2d.rotated
import java.io.File

class Day13(
    fileName: String? = null,
    text: String = File(fileName ?: error("No fileName or text provided")).readText()
) : ChallengeDay {

    private val patterns = text.splitToSequence(AocUtils.DOUBLE_LINE_SEPARATOR).map { Pattern(it.lines()) }

    override fun part1(): Int = patterns.map(Pattern::findMirror).sumOf(::toSummary)
    override fun part2(): Int = patterns.map(Pattern::findSmuggedMirror).sumOf(::toSummary)

    private fun toSummary(mirror: Mirror) = when (mirror) {
        is VerMirror -> mirror.x
        is HorMirror -> mirror.y * 100
    }

    private data class Pattern(val grid: List<String>) {

        fun findMirror(toMirrorPosition: List<String>.() -> Int? = { (1..<size).firstOrNull { isMirrorAt(it) } }): Mirror =
            grid.toMirrorPosition()?.let(::HorMirror) ?: grid.rotated().toMirrorPosition()?.let(::VerMirror)
            ?: error("No mirror found in ${grid.gridAsString()}")

        fun findSmuggedMirror(): Mirror = findMirror { (1..<size).firstOrNull { isSmudgedMirrorAt(it) } }

        private fun List<String>.isMirrorAt(y: Int): Boolean {
            require(y >= 1)
            var yAbove = y - 1
            var yBelow = y
            while (yAbove >= 0 && yBelow < size) {
                for (x in this[y].indices) {
                    if (this[yAbove][x] != this[yBelow][x]) {
                        return false
                    }
                }
                yAbove--
                yBelow++
            }
            return true
        }

        private fun List<String>.isSmudgedMirrorAt(y: Int): Boolean {
            require(y >= 1)
            var yAbove = y - 1
            var yBelow = y
            var smudges = 0
            while (yAbove >= 0 && yBelow < size) {
                for (x in this[y].indices) {
                    if (this[yAbove][x] != this[yBelow][x]) {
                        if (smudges > 1) {
                            return false
                        }
                        smudges++
                    }
                }
                yAbove--
                yBelow++
            }
            return smudges == 1
        }
    }

    private sealed interface Mirror
    private data class HorMirror(val y: Int) : Mirror
    private data class VerMirror(val x: Int) : Mirror
}

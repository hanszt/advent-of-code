package aoc.secret_entrance

import aoc.utils.ChallengeDay
import aoc.utils.oneOrMoreWhiteSpaces
import java.nio.file.Path
import kotlin.io.path.readLines

/**
 * [aoc.utils.Tag.AOC_MATH]
 */
class Day06(private val lines: List<String>) : ChallengeDay {

    val operators = lines.last().trim().split(oneOrMoreWhiteSpaces).map { it.trim().single() }

    constructor(path: Path) : this(path.readLines())

    override fun part1(): Long {
        val nrLists = lines.dropLast(1)
            .map { line -> line.trim().split(oneOrMoreWhiteSpaces).map { it.trim().toInt() } }

        return (0 until operators.size).sumOf { i ->
            val operator = operators[i]
            nrLists.fold(determineInitial(operator)) { acc, nrs -> operator.apply(acc, nrs[i]) }
        }
    }

    override fun part2(): Long {
        val nrLists = parseToCephalopodMathNrLists()
        return (nrLists.lastIndex downTo  0).sumOf { i ->
            val operator = operators[i]
            nrLists[i].fold(determineInitial(operator)) { acc, nr -> operator.apply(acc, nr) }
        }
    }

    /**
     * 123 328  51 64
     *  45 64  387 23
     *   6 98  215 314
     *
     * The rightmost problem is 4 + 431 + 623 = 1058
     * The second problem from the right is 175 * 581 * 32 = 3253600
     * The third problem from the right is 8 + 248 + 369 = 625
     * Finally, the leftmost problem is 356 * 24 * 1 = 8544
     */
    private fun parseToCephalopodMathNrLists(): List<List<Int>> {
        val maxLength = lines.maxOf { it.length }
        val nrLists = mutableListOf<List<Int>>()
        val nrs = mutableListOf<Int>()
        for (i in (maxLength - 1) downTo 0) {
            val s = buildString {
                for (line in lines) {
                    append(line.getOrNull(i)?.takeIf { it.isDigit() }?.digitToInt() ?: continue)
                }
            }
            if (s.isBlank()) {
                nrLists.addFirst(nrs.toList())
                nrs.clear()
            } else nrs.add(s.toInt())
        }
        nrLists.addFirst(nrs.toList())
        return nrLists
    }

    private fun Char.apply(result: Long, nr: Int): Long = when (this) {
        '+' -> result + nr
        '*' -> result * nr
        else -> error("Invalid operator: $this")
    }

    private fun determineInitial(operator: Char): Long = when (operator) {
        '+' -> 0L
        '*' -> 1L
        else -> error("Invalid operator: $operator")
    }
}

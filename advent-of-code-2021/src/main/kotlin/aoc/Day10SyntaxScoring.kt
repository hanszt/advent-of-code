package aoc

import java.io.File

internal object Day10SyntaxScoring : ChallengeDay {

    private val closingToScoresPart1 = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)

    fun part1(path: String): Int =
        File(path).readLines().sumOf {
            val (corrupted) = toCorruptedClosingCharAndRemainingChars(it)
            closingToScoresPart1[corrupted] ?: 0
        }

    fun toCorruptedClosingCharAndRemainingChars(s: String): Pair<Char, List<Char>> {
        val chars = s.toMutableList()
        var prevSize: Int
        while (true) {
            prevSize = chars.size
            val closingChar = chars.removeValidUnitChunk()
            if (chars.size == prevSize) {
                return closingChar to chars
            }
        }
    }

    private val openingToClosingChars = mapOf('(' to ')', '[' to ']', '{' to '}', '<' to '>')

    private fun MutableList<Char>.removeValidUnitChunk(): Char {
        for (index in 0 until lastIndex) {
            val current = this[index]
            val next = this[index + 1]
            if (current in openingToClosingChars.keys && next in openingToClosingChars.values) {
                val expectedClosing = openingToClosingChars[current]
                return if (next == expectedClosing) {
                    removeAt(index + 1)
                    removeAt(index)
                    next
                } else next
            }
        }
        return ' '
    }

    fun part2(path: String): Long =
        File(path).useLines { lines ->
        lines.map(::toCorruptedClosingCharAndRemainingChars)
            .filter { (closing) -> isIncomplete(closing) }
            .map { (_, chars) -> chars.reversed().mapNotNull(openingToClosingChars::get) }
            .map(::toScoreCompletionList)
            .toMiddleScore()
    }

    private fun Sequence<Long>.toMiddleScore() = sorted().toList().let { it[it.size / 2] }

    private fun isIncomplete(closing: Char) = closing.isWhitespace()

    private val closingToScorePart2 = mapOf(')' to 1, ']' to 2, '}' to 3, '>' to 4)

    private fun toScoreCompletionList(chars: List<Char>): Long =
        chars.mapNotNull(closingToScorePart2::get).map(Int::toLong).reduce { score, cur -> score * 5 + cur }

    override fun part1() = part1(ChallengeDay.inputDir + "/day10.txt")
    override fun part2() = part2(ChallengeDay.inputDir + "/day10.txt")
}

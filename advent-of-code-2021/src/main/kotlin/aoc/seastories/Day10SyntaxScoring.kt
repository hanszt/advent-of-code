package aoc.seastories

import java.io.File

internal class Day10SyntaxScoring(private val inputPath: String) : ChallengeDay {

    private val closingToScoresPart1 = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)

    override fun part1(): Int = File(inputPath).readLines().sumOf {
        val (corrupted) = toCorruptedClosingCharAndRemainingChars(it)
        closingToScoresPart1[corrupted] ?: 0
    }

    override fun part2(): Long =
        File(inputPath).useLines { lines ->
            lines.map(::toCorruptedClosingCharAndRemainingChars)
                .filter { (closing) -> isIncomplete(closing) }
                .map { (_, chars) -> chars.reversed().mapNotNull(openingToClosingChars::get) }
                .map(::toCompletionListScore)
                .toMiddleScore()
        }

    private fun Sequence<Long>.toMiddleScore() = sorted().toList().let { it[it.size / 2] }

    private fun isIncomplete(closing: Char) = closing.isWhitespace()

    private val closingToScorePart2 = mapOf(')' to 1, ']' to 2, '}' to 3, '>' to 4)

    private fun toCompletionListScore(chars: List<Char>): Long =
        chars.mapNotNull(closingToScorePart2::get).map(Int::toLong).reduce { score, cur -> score * 5 + cur }

    companion object {

        private val openingToClosingChars = mapOf('(' to ')', '[' to ']', '{' to '}', '<' to '>')

        tailrec fun toCorruptedClosingCharAndRemainingChars(
            s: String, chars: MutableList<Char> = s.toMutableList()
        ): Pair<Char, List<Char>> {
            val prevSize = chars.size
            val closingChar = chars.removeValidUnitChunk()
            if (chars.size == prevSize) {
                return closingChar to chars
            }
            return toCorruptedClosingCharAndRemainingChars(s, chars)
        }

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
    }
}

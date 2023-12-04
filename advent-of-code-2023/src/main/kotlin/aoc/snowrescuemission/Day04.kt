package aoc.snowrescuemission

import aoc.utils.ChallengeDay
import aoc.utils.toSetOf
import java.io.File

class Day04(
    fileName: String? = null,
    private val lines: List<String> = fileName?.let { File(it).readLines() } ?: emptyList()
) : ChallengeDay {

    override fun part1(): Int = lines.map(::card).sumOf(::calculatePoints)

    override fun part2(): Int {
        val cards = lines.map(::card)
        copyCards(cards)
        return cards.sumOf(Card::count)
    }

    private fun calculatePoints(card: Card): Int = card.nrsInHand.filter { it in card.winningNrs }.fold(0) { points, _ ->
        if (points == 0) 1 else points * 2
    }

    private fun card(line: String): Card {
        val (_, winning, cards) = line.split(": ", " | ")
        val winningNrs = winning.split(' ').filter(String::isNotEmpty).toSetOf(String::toInt)
        val nrsInHand = cards.split(' ').filter(String::isNotEmpty).toSetOf(String::toInt)
        return Card(winningNrs, nrsInHand)
    }

    data class Card(val winningNrs: Set<Int>, val nrsInHand: Set<Int>) {

        var count = 1
        fun nrOfMatchingNrs() = nrsInHand.count { it in winningNrs }
    }

    private fun copyCards(cards: List<Card>) {
        for ((index, card) in cards.withIndex()) {
            for (i in 0..<card.count) {
                val matchingNrs = card.nrOfMatchingNrs()
                for (j in 1.. matchingNrs) {
                    val nextI = index + j
                    cards.getOrNull(nextI)?.let { it.count++ }
                }
            }
        }
    }
}

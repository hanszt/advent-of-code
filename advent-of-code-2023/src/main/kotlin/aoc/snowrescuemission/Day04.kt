package aoc.snowrescuemission

import aoc.utils.ChallengeDay
import aoc.utils.toSetOf
import java.io.File

class Day04(fileName: String, ) : ChallengeDay {

    private val cards = File(fileName).useLines { lines -> lines.map(::card).toList() }

    override fun part1(): Int = cards.sumOf(::calculatePoints)

    override fun part2(): Int {
        val counts = IntArray(cards.size) { 1 }
        for ((i, card) in cards.withIndex()) {
            val matchingCount = (card.nrsInHand intersect card.winningNrs).size
            for (j in 0..<matchingCount) {
                counts[i + j + 1] += counts[i]
            }
        }
        return counts.sum()
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

    data class Card(val winningNrs: Set<Int>, val nrsInHand: Set<Int>)
}

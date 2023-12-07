package aoc.snowrescuemission

import aoc.utils.ChallengeDay
import java.io.File

class Day07(
    fileName: String? = null,
    private val lines: List<String> = fileName?.let { File(it).readLines() } ?: error("No text or fileName provided")
) : ChallengeDay {

    override fun part1(): Long {
        return lines.asSequence()
            .map(Hand::parse)
            .sortedWith(compareBy(Hand::type).then(Hand::strength))
            .withIndex()
            .fold(0) { acc, (i, hand) -> acc + ((i + 1) * hand.bid) }
    }

    data class Hand(val cards: String, val bid: Int) {

        constructor(cards: String) : this(cards, bid = 0)

        fun type(): Int {
            // Five of a kind
            if (cards.toSet().size == 1) return 6
            val groupedCards = cards.groupBy { it }
            if (groupedCards.size == 2) {
                // Four of a kind
                if (groupedCards.values.any { it.size == 4 }) return 5
                // Full house
                if (groupedCards.values.any { it.size == 3 }) return 4
            }
            if (groupedCards.size == 3) {
                // Three of a kind
                if (groupedCards.values.any { it.size == 3 }) return 3
                // Two pair
                if (groupedCards.values.distinctBy(List<Char>::size).size == 2) return 2
            }
            // One pair
            if (groupedCards.size == 4) return 1
            // High card
            return 0
        }

        fun strength(other: Hand): Int {
            for ((c, co) in cards.zip(other.cards)) {
                val strength = cardStrengths.indexOf(c)
                val otherStrength = cardStrengths.indexOf(co)
                if (strength > otherStrength) {
                    return -1
                }
                if (strength < otherStrength) {
                    return 1
                }
            }
            return 0
        }

        companion object {

            fun parse(line: String): Hand = line.split(' ').let { (cards, bit) -> Hand(cards, bit.toInt()) }

            val cardStrengths = listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')
        }

    }


    override fun part2(): Long {
        TODO()
    }
}

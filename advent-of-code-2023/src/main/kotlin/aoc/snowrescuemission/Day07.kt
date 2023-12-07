package aoc.snowrescuemission

import aoc.utils.ChallengeDay
import aoc.utils.group
import java.io.File

class Day07(
    fileName: String? = null,
    private val lines: List<String> = fileName?.let { File(it).readLines() } ?: error("No text or fileName provided")
) : ChallengeDay {

    override fun part1(): Long = solve(compareBy(Hand::type).then(Hand::compareByStrengthPart1))
    override fun part2(): Long = solve(compareBy(Hand::typeWithJoker).then(Hand::compareByStrengthPart2))

    private fun solve(handComparator: Comparator<Hand>): Long = lines.asSequence()
        .map(Hand::parse)
        .sortedWith(handComparator)
        .withIndex()
        .fold(0) { acc, (index, hand) ->
            val rank = index + 1
            acc + (rank * hand.bid)
        }

    data class Hand(val cards: String, val bid: Int = 0) {

        fun typeWithJoker(): Type = type(cards.filterNot { it == 'J' }.group
            .maxByOrNull { it.value.size }
            ?.key?.let { cards.replace('J', it) } ?: cards
        )


        fun type(cards: String = this.cards): Type {
            if (cards.toSet().size == 1) return Type.FIVE_OF_A_KIND
            cards.group.apply {
                if (size == 2) {
                    if (values.any { it.size == 4 }) return Type.FOUR_OF_A_KIND
                    if (values.any { it.size == 3 }) return Type.FULL_HOUSE
                }
                if (size == 3) {
                    if (values.any { it.size == 3 }) return Type.THREE_OF_A_KIND
                    if (values.distinctBy(List<Char>::size).size == 2) return Type.TWO_PAIR
                }
                if (size == 4) return Type.ONE_PAIR
            }
            return Type.HIGH_CARD
        }

        /**
         * Ordered by type strength the lowest first and the highest last
         */
        enum class Type { HIGH_CARD, ONE_PAIR, TWO_PAIR, THREE_OF_A_KIND, FULL_HOUSE, FOUR_OF_A_KIND, FIVE_OF_A_KIND }

        fun compareByStrengthPart1(otherHand: Hand): Int = compareByStrength(otherHand, cardStrengthsPart1)
        fun compareByStrengthPart2(otherHand: Hand): Int = compareByStrength(otherHand, cardStrengthsPart2)

        private fun compareByStrength(otherHand: Hand, chars: List<Char>): Int {
            for ((card, otherCard) in cards zip otherHand.cards) {
                val strength = chars.indexOf(card)
                val otherStrength = chars.indexOf(otherCard)
                if (strength > otherStrength) return -1
                if (strength < otherStrength) return 1
            }
            return 0
        }

        companion object {
            fun parse(line: String): Hand = line.split(' ').let { (cards, bit) -> Hand(cards, bit.toInt()) }

            private val cardStrengthsPart1 = listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')
            private val cardStrengthsPart2 = listOf('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J')
        }
    }
}

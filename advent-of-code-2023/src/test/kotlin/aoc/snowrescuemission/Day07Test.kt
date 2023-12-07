package aoc.snowrescuemission

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day07Test {

    @Test
    fun part1Test() {
        Day07("input/day07.txt").part1() shouldBe 253_910_319
    }

    @Test
    fun testPart1Test() {
        val lines = listOf(
            "32T3K 765",
            "T55J5 684",
            "KK677 28",
            "KTJJT 220",
            "QQQJA 483",
        )
        Day07(lines = lines).part1() shouldBe 6_440
    }

    @Test
    fun testSortedByType() {
        val lines = listOf(
            "KKKKK",
            "KKKK2",
            "KKK22",
            "KKK23",
            "KK234",
            "K2345",
        ).map(Day07::Hand)

        lines.sortedBy(Day07.Hand::type).map(Day07.Hand::cards) shouldBe listOf(
            "K2345",
            "KK234",
            "KKK23",
            "KKK22",
            "KKKK2",
            "KKKKK"
        )
    }


    @Test
    fun part2Test() {
        Day07("input/day07.txt").part2() shouldBe 0
    }

}
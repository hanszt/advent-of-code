package aoc.snowrescuemission

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day07Test {

    private companion object {
        private val day07 = Day07("input/day07-dr.txt")
        private val testDay07 = Day07(
            lines = listOf(
                "32T3K 765",
                "T55J5 684",
                "KK677 28",
                "KTJJT 220",
                "QQQJA 483",
            )
        )
    }

    @Test
    fun part1Test() {
        day07.part1() shouldBe 253_910_319
    }

    @Test
    fun part2Test() {
        day07.part2() shouldBe 254_083_736
    }

    @Test
    fun testPart1Test() {
        testDay07.part1() shouldBe 6_440
    }


    @Test
    fun testPart2Test() {
        testDay07.part2() shouldBe 5_905
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

}
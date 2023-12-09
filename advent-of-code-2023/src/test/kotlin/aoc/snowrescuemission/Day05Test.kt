package aoc.snowrescuemission

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day05Test {

    private companion object {
        private val day05 = Day05("input/day05.txt")
    }


    @Test
    fun testPart1() {
        day05.part1() shouldBe 165_788_812
    }

    @Test
    fun testPart2Test() {
        Day05("input/day05test.txt").part2() shouldBe 46
    }

    @Test
    fun testPart2() {
        day05.part2() shouldBe 1_928_058
    }
}
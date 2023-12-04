package aoc.snowrescuemission

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day04Test {

    companion object {
        private val day04 = Day04("input/day04.txt")
    }

    @Test
    fun testPart1() {
        day04.part1() shouldBe 24_848
    }

    @Test
    fun testPart2() {
        day04.part2() shouldBe 7_258_152
    }
}
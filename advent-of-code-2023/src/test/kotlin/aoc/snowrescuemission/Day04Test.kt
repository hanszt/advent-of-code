package aoc.snowrescuemission

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day04Test {

    @Test
    fun testPart1() {
        Day04("input/day04.txt").part1() shouldBe 24_848
    }

    @Test
    fun testPart2() {
        Day04("input/day04.txt").part2() shouldBe 7_258_152
    }
}
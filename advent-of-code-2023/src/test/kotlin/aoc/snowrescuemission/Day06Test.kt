package aoc.snowrescuemission

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day06Test {

    @Test
    fun part1Test() {
        Day06("input/day06.txt").part1() shouldBe 2_269_432
    }

    @Test
    fun testPart1Test() {
        Day06(
            lines = listOf(
                "Time:      7  15   30",
                "Distance:  9  40  200",
            )
        ).part1() shouldBe 288
    }

    @Test
    fun part2Test() {
        Day06("input/day06.txt").part2() shouldBe 35_865_985
    }

}
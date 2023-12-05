package aoc.snowrescuemission

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day05Test {

    @Test
    fun testPart1() {
        Day05("input/day05.txt").part1() shouldBe 165_788_812
    }

    @Test
    fun testPart2Test() {
        Day05("input/day05test.txt").part2() shouldBe 46
    }

    @Test
    fun testPart2() {
        Day05("input/day05.txt").part2() shouldBe 0
    }
}
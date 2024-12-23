package aoc.snowrescuemission

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day20Test {

    @Test
    fun testPart1() {
        Day20("input/day20.txt").part1() shouldBe 739960225
    }

    @Test
    fun testPart1dr() {
        Day20("input/day20dr.txt").part1() shouldBe 777666211
    }

    @Test
    fun testPart2() {
        Day20("input/day20.txt").part2() shouldBe 231897990075517
    }

    @Test
    fun testPart2dr() {
        Day20("input/day20dr.txt").part2() shouldBe 243081086866483
    }
}

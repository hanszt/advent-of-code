package aoc

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day01Test {

    @Test
    fun `test part 1`() {
        Day01("input/day01.txt").part1() shouldBe 0
    }

    @Test
    fun `test part 2`() {
        Day01("input/day01.txt").part2() shouldBe 0
    }
}
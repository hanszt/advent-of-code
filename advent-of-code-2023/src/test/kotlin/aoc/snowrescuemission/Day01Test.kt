package aoc.snowrescuemission

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day01Test {

    @Test
    fun `test part 1`() {
        Day01("input/day01-dr.txt").part1() shouldBe 55_172
    }

    @Test
    fun `test part 2 small`() {
        val lines = listOf(
            "two1nine",
            "eightwothree",
            "abcone2threexyz",
            "xtwone3four",
            "4nineeightseven2",
            "zoneight234",
            "7pqrstsixteen"
        )

        Day01(lines = lines).part2() shouldBe 281
    }

    @Test
    fun `failing line`() {
        Day01(lines = listOf("8twofivexpvdjljffpcnbjtkninefive")).part2() shouldBe 85
    }

    @Test
    fun `test part 2`() {
        Day01("input/day01-dr.txt").part2() shouldBe 54_925
    }
}
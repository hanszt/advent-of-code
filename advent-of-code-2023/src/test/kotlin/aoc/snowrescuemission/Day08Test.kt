package aoc.snowrescuemission

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day08Test {

    companion object {
        private val day08 = Day08("input/day08.txt")
    }

    @Test
    fun `test part1`() {
        day08.part1() shouldBe 16_043
    }

    @Test
    fun `test input part2`() {
        val input = """
            LR

            11A = (11B, XXX)
            11B = (XXX, 11Z)
            11Z = (11B, XXX)
            22A = (22B, XXX)
            22B = (22C, 22C)
            22C = (22Z, 22Z)
            22Z = (22B, 22B)
            XXX = (XXX, XXX)
        """.trimIndent()
        Day08(text = input).part2() shouldBe 6
    }

    @Test
    fun `test part2`() {
        day08.part2() shouldBe 15_726_453_850_399
    }
}
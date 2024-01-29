package aoc.seastories

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day02DiveTest {

    private companion object {
        private val day02DiveTestInput = Day02Dive("input/day2test.txt")
        private val day02Dive = Day02Dive("input/day2.txt")
    }

    @Test
    fun `part 1 test input`() = assertEquals(150, day02DiveTestInput.part1())

    @Test
    fun `part 1 result`() = assertEquals(2_019_945, day02Dive.part1().also(::println))

    @Test
    fun `part 2 test input`() = assertEquals(900, day02DiveTestInput.part2())

    @Test
    fun `part 2 result`() = assertEquals(1_599_311_480, day02Dive.part2().also(::println))
}

package aoc.seastories

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day02DiveTest {

    @Test
    fun `part 1 test input`() = assertEquals(150, Day02Dive.part1("input/day2test.txt"))

    @Test
    fun `part 1 result`() = assertEquals(2_019_945, Day02Dive.part1().also(::println))

    @Test
    fun `part 2 test input`() = assertEquals(900, Day02Dive.part2("input/day2test.txt"))

    @Test
    fun `part 2 result`() = assertEquals(1_599_311_480, Day02Dive.part2().also(::println))
}

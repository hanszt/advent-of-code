package aoc.seastories

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day02DiveTest {

    @Test
    fun `part 1 test input`() = assertEquals(150, Day02Dive("input/day2test.txt").part1())

    @Test
    fun `part 1 result`() = assertEquals(2_019_945, Day02Dive("input/day2.txt").part1().also(::println))

    @Test
    fun `part 2 test input`() = assertEquals(900, Day02Dive("input/day2test.txt").part2())

    @Test
    fun `part 2 result`() = assertEquals(1_599_311_480, Day02Dive("input/day2.txt").part2().also(::println))
}

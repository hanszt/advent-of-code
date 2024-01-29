package aoc.seastories

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day08SevenSegmentSearchTest {

    private val day08SevenSegmentSearchTestInput = Day08SevenSegmentSearch("input/day8test.txt")
    private val day08SevenSegmentSearch = Day08SevenSegmentSearch("input/day8.txt")

    @Test
    fun `part 1 test input`() = assertEquals(26, day08SevenSegmentSearchTestInput.part1())

    @Test
    fun `part 1 result`() = assertEquals(369, day08SevenSegmentSearch.part1().also(::println))

    @Test
    fun `part 2 test input`() = assertEquals(61_229, day08SevenSegmentSearchTestInput.part2())

    @Test
    fun `part 2 result`() = assertEquals(1_031_553, day08SevenSegmentSearch.part2().also(::println))
}

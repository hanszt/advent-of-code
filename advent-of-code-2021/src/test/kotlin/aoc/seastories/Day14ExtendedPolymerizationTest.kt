package aoc.seastories

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day14ExtendedPolymerizationTest {

    @Test
    fun `part 1 test input`() = assertEquals(1_588, Day14ExtendedPolymerization("input/day14test.txt").part1())

    @Test
    fun `part 1 result`() = assertEquals(4_244, Day14ExtendedPolymerization("input/day14.txt").part1().also(::println))

    @Test
    fun `part 2 test input`() = assertEquals(2_188_189_693_529, Day14ExtendedPolymerization("input/day14test.txt").part2())

    @Test
    fun `part 2 result`() = assertEquals(4_807_056_953_866, Day14ExtendedPolymerization("input/day14.txt").part2().also(::println))
}

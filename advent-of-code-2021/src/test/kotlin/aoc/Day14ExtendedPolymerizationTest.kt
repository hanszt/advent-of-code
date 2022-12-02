package aoc

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day14ExtendedPolymerizationTest {

    @Test
    fun `part 1 test input`() = assertEquals(1_588, Day14ExtendedPolymerization.part1("input/day14test.txt"))

    @Test
    fun `part 1 result`() = assertEquals(4_244, Day14ExtendedPolymerization.part1().also(::println))

    @Test
    fun `part 2 test input`() = assertEquals(2_188_189_693_529, Day14ExtendedPolymerization.part2("input/day14test.txt"))

    @Test
    fun `part 2 result`() = assertEquals(4_807_056_953_866, Day14ExtendedPolymerization.part2().also(::println))
}

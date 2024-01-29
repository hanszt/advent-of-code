package aoc.seastories

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day12PassagePathingTest {

    private val day12PassagePathingTestInput = Day12PassagePathing("input/day12test.txt")
    private val day12PassagePathingMediumInput = Day12PassagePathing("input/day12test-medium.txt")
    private val day12PassagePathing = Day12PassagePathing("input/day12.txt")

    @Test
    fun `part 1 test input small`() = assertEquals(10, day12PassagePathingTestInput.part1())

    @Test
    fun `part 1 test input medium`() = assertEquals(19, day12PassagePathingMediumInput.part1())

    @Test
    fun `part 1 result`() = assertEquals(3_298, day12PassagePathing.part1().also(::println))

    @Test
    fun `part 2 test input small`() = assertEquals(36, day12PassagePathingTestInput.part2())

    @Test
    fun `part 2 test input medium`() = assertEquals(103, day12PassagePathingMediumInput.part2())

    @Test
    fun `part 2 result`() = assertEquals(93_572, day12PassagePathing.part2().also(::println))
}

package aoc

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day12PassagePathingTest {

    @Test
    fun `part 1 test input small`() = assertEquals(10, Day12PassagePathing.part1("input/day12test.txt"))

    @Test
    fun `part 1 test input medium`() = assertEquals(19, Day12PassagePathing.part1("input/day12test-medium.txt"))

    @Test
    fun `part 1 result`() = assertEquals(3_298, Day12PassagePathing.part1().also(::println))

    @Test
    fun `part 2 test input small`() = assertEquals(36, Day12PassagePathing.part2("input/day12test.txt"))

    @Test
    fun `part 2 test input medium`() = assertEquals(103, Day12PassagePathing.part2("input/day12test-medium.txt"))

    @Test
    fun `part 2 result`() = assertEquals(93_572, Day12PassagePathing.part2().also(::println))

}

package aoc

import aoc.Day11DumboOctopus.Octopus
import aoc.Day11DumboOctopus.simulateStep
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Test
import utils.*

internal class Day11DumboOctopusTest {

    @Test
    fun `part 1 test input`() = Assertions.assertEquals(1_656, Day11DumboOctopus.part1("input/day11test.txt"))

    @Test
    fun `part 1 result`() = Assertions.assertEquals(1_683, Day11DumboOctopus.part1().also(::println))

    @Test
    fun `part 2 test input`() = Assertions.assertEquals(195, Day11DumboOctopus.part2("input/day11test.txt"))

    @Test
    fun `part 2 result`() = Assertions.assertEquals(788, Day11DumboOctopus.part2().also(::println))

    @Test
    fun `test simulate one step`() {
        val expected = """
            34543
            40004
            50005
            40004
            34543
        """.trimIndent().lines().toIntGrid(Char::digitToInt).toGridOf(::Octopus)
        val grid = """
            11111
            19991
            19191
            19991
            11111
        """.trimIndent().lines().toIntGrid(Char::digitToInt).toGridOf(::Octopus)

        grid.simulateStep()

        println("Result:")
        println(grid.gridAsString(1, selector = Octopus::energyLevel).withColor(BRIGHT_YELLOW))

        assertArrayEquals(expected, grid)
    }
}

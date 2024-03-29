package aoc.seastories

import aoc.seastories.Day11DumboOctopus.Companion.simulateStep
import aoc.seastories.Day11DumboOctopus.Octopus
import aoc.utils.BRIGHT_YELLOW
import aoc.utils.gridAsString
import aoc.utils.toGridOf
import aoc.utils.toIntGrid
import aoc.utils.withColor
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day11DumboOctopusTest {

    private val day11DumboOctopusTestInput = Day11DumboOctopus("input/day11test.txt")
    private val day11DumboOctopus = Day11DumboOctopus("input/day11.txt")

    @Test
    fun `part 1 test input`() = assertEquals(1_656, day11DumboOctopusTestInput.part1())

    @Test
    fun `part 1 result`() = assertEquals(1_683, day11DumboOctopus.part1().also(::println))

    @Test
    fun `part 2 test input`() = assertEquals(195, day11DumboOctopusTestInput.part2())

    @Test
    fun `part 2 result`() = assertEquals(788, day11DumboOctopus.part2().also(::println))

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

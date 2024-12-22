package aoc.seastories

import aoc.seastories.Day11DumboOctopus.Companion.simulateStep
import aoc.seastories.Day11DumboOctopus.Octopus
import aoc.utils.BRIGHT_YELLOW
import aoc.utils.grid2d.gridAsString
import aoc.utils.grid2d.toGrid
import aoc.utils.withColor
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class Day11DumboOctopusTest {

    private val day11DumboOctopusTestInput = Day11DumboOctopus("input/day11test.txt")
    private val day11DumboOctopus = Day11DumboOctopus("input/day11.txt")

    @Test
    fun `part 1 test input`() {
        day11DumboOctopusTestInput.part1() shouldBe 1_656
    }

    @Test
    fun `part 1 result`() {
        day11DumboOctopus.part1().also(::println) shouldBe 1_683
    }

    @Test
    fun `part 2 test input`() {
        day11DumboOctopusTestInput.part2() shouldBe 195
    }

    @Test
    fun `part 2 result`() {
        day11DumboOctopus.part2().also(::println) shouldBe 788
    }

    @Test
    fun `test simulate one step`() {
        val expected = """
            34543
            40004
            50005
            40004
            34543
        """.trimIndent().lines().toGrid { Octopus(it.digitToInt()) }
        val grid = """
            11111
            19991
            19191
            19991
            11111
        """.trimIndent().lines().toGrid { Octopus(it.digitToInt()) }

        grid.simulateStep()

        println("Result:")
        println(grid.gridAsString(1, selector = Octopus::energyLevel).withColor(BRIGHT_YELLOW))

        grid shouldBe expected
    }
}

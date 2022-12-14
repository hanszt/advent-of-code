package aoc.seastories

import aoc.seastories.Day05HydrothermalVenture.asGrid
import aoc.seastories.Day05HydrothermalVenture.countIntersections
import aoc.seastories.Day05HydrothermalVenture.toVentureLines
import aoc.utils.*
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.io.File

internal class Day05HydrothermalVentureTest {

    @Test
    fun `part 1 test input`() = Day05HydrothermalVenture.part1("input/day5test.txt").shouldBe(5)

    @Test
    fun `part 1 result`() = Day05HydrothermalVenture.part1().also(::println).shouldBe(7_085)

    @Test
    fun `part 2 test input`() = File("input/day5test.txt")
        .toVentureLines()
        .asGrid().also(::printGrid)
        .countIntersections()
        .shouldBe(12)

    @Test
    fun `part 2 result`() = File("input/day5.txt").toVentureLines().asGrid()
        .countIntersections().also(::println).shouldBe(20_271)

    private fun printGrid(grid: Array<IntArray>) = println(grid.gridAsString(alignment = 2) {
        if (it > 0) it.toString().withColors(BROWN_BG, YELLOW)
        else ".".withColor(BROWN_BG)
    })
}

package aoc.seastories

import aoc.seastories.Day05HydrothermalVenture.Companion.asGrid
import aoc.seastories.Day05HydrothermalVenture.Companion.countIntersections
import aoc.seastories.Day05HydrothermalVenture.Companion.toVentureLines
import aoc.utils.BgColor
import aoc.utils.TextColor
import aoc.utils.grid2d.gridAsString
import aoc.utils.withColor
import aoc.utils.withColors
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.io.File

internal class Day05HydrothermalVentureTest {

    @Test
    fun `part 1 test input`() {
        Day05HydrothermalVenture("input/day5test.txt").part1() shouldBe 5
    }

    @Test
    fun `part 1 result`() {
        Day05HydrothermalVenture("input/day5.txt").part1().also(::println) shouldBe 7_085
    }

    @Test
    fun `part 2 test input`() {
        File("input/day5test.txt")
            .useLines { it.toVentureLines() }
            .asGrid().also(::printGrid)
            .countIntersections()
            .shouldBe(12)
    }

    @Test
    fun `part 2 result`() {
        File("input/day5.txt").useLines { it.toVentureLines() }.asGrid()
            .countIntersections().also(::println) shouldBe 20_271
    }

    private fun printGrid(grid: Array<IntArray>) = println(grid.gridAsString(spacing = 2) {
        if (it > 0) it.toString().withColors(TextColor.YELLOW, BgColor.BROWN)
        else ".".withColor(BgColor.BROWN)
    })
}

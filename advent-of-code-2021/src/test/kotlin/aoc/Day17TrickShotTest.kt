package aoc

import aoc.Day17TrickShot.Probe
import aoc.utils.model.GridPoint2D
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import aoc.utils.*
import aoc.utils.model.GridPoint2D.Companion.by

internal class Day17TrickShotTest {

    @Test
    fun `part 1 test input`() = assertEquals(45, Day17TrickShot.part1(20..30, -10..-5))

    @Test
    fun `part 1 result`() = assertEquals(7_381, Day17TrickShot.part1().also(::println))

    @Test
    fun `part 2 test input`() = assertEquals(112, Day17TrickShot.part2(20..30, -10..-5))

    @Test
    fun `part 2 result`() = assertEquals(3_019, Day17TrickShot.part2().also(::println))

    @Test
    fun `part 1 answer analytical`() {
        val highestPosYTest = calculateHighestPosition(-10)
        val highestPosY = calculateHighestPosition(-122)

        assertAll(
            { assertEquals(45, highestPosYTest) },
            { assertEquals(7_381, highestPosY) }
        )
    }

    /**
     * @param bottomYTargetArea
     * @return
     */
    private fun calculateHighestPosition(bottomYTargetArea: Int): Int = sumNaturalNrs(bound = -(bottomYTargetArea + 1))

    @Test
    fun `display traveled path`() {
        val probe = Probe(velocity = 8 by 3)
        val path = probe.getPath()
        val maxX = path.maxOf(GridPoint2D::x)

        val yPosStats = path.stream()
            .mapToInt(GridPoint2D::y)
            .summaryStatistics()

        val grid = Array(yPosStats.max - yPosStats.min + 1) { IntArray(maxX + 1) }
        path.forEach { (x, y) -> grid[y - yPosStats.min][x] = 1 }
        println(grid.mirroredHorizontally().gridAsString(1,"") {
            if (it == 0) ".".withColors(BRIGHT_BLUE, ICY_BG) else "#".withColors(RED, ICY_BG) })

        assertTrue(path.isNotEmpty())
    }
}

package aoc.seastories

import aoc.seastories.Day17TrickShot.Probe
import aoc.utils.*
import aoc.utils.model.GridPoint2D
import aoc.utils.model.GridPoint2D.Companion.by
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class Day17TrickShotTest {

    @Test
    fun `part 1 test input`() {
        Day17TrickShot.part1(20..30 by -10..-5) shouldBe 45
    }

    @Test
    fun `part 1 result`() {
        Day17TrickShot.part1().also(::println) shouldBe 7_381
    }

    @Test
    fun `part 2 test input`() {
        Day17TrickShot.part2(20..30 by -10..-5) shouldBe 112
    }

    @Test
    fun `part 2 result`() {
        Day17TrickShot.part2().also(::println) shouldBe 3_019
    }

    @Test
    fun `part 1 answer analytical`() {
        val highestPosYTest = calculateHighestPosition(-10)
        val highestPosY = calculateHighestPosition(-122)

        assertSoftly {
            highestPosYTest shouldBe 45
            highestPosY shouldBe 7_381
        }
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
        println(grid.mirroredVertically().gridAsString(1,"") {
            if (it == 0) ".".withColors(BRIGHT_BLUE, ICY_BG) else "#".withColors(RED, ICY_BG) })

        path.shouldNotBeEmpty()
    }
}

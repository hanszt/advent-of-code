package aoc.seastories

import aoc.seastories.Day17TrickShot.Probe
import aoc.utils.BgColor
import aoc.utils.TextColor.Companion.BRIGHT_BLUE
import aoc.utils.TextColor.Companion.RED
import aoc.utils.grid2d.GridPoint2D
import aoc.utils.grid2d.GridPoint2D.Companion.by
import aoc.utils.grid2d.by
import aoc.utils.grid2d.gridAsString
import aoc.utils.grid2d.mirroredVertically
import aoc.utils.sumNaturalNrs
import aoc.utils.withColors
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.math.max
import kotlin.math.min

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

        class Stats {
            var min = Int.MAX_VALUE
            var max = Int.MIN_VALUE
            fun compute(v: Int): Stats {
                min = min(v, min)
                max = max(v, max)
                return this
            }
        }

        val yPosStats = path.fold(Stats()) { s, p -> s.compute(p.y) }

        val grid = Array(yPosStats.max - yPosStats.min + 1) { IntArray(maxX + 1) }
        path.forEach { (x, y) -> grid[y - yPosStats.min][x] = 1 }
        println(grid.mirroredVertically().gridAsString(1, "") {
            if (it == 0) ".".withColors(BRIGHT_BLUE, BgColor.ICY) else "#".withColors(RED, BgColor.ICY)
        })

        path.shouldNotBeEmpty()
    }
}

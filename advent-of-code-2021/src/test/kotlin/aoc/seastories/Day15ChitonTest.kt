package aoc.seastories

import aoc.utils.*
import aoc.utils.graph.WeightedNode
import aoc.utils.graph.dijkstra
import aoc.utils.graph.toWeightedGraph
import aoc.utils.grid2d.GridPoint2D
import aoc.utils.grid2d.GridPoint2D.Companion.by
import aoc.utils.grid2d.gridAsString
import aoc.utils.grid2d.toMutableGrid
import aoc.utils.grid2d.toMutableIntGrid
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldNotBeBlank
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Timeout
import java.io.File
import java.util.concurrent.TimeUnit
import kotlin.random.Random

internal class Day15ChitonTest {

    private companion object {
        private val day15Test = Day15Chiton("input/day15test.txt")
        private val day15 = Day15Chiton("input/day15.txt")
    }

    @Test
    fun `part 1 test input`() {
        day15Test.part1() shouldBe 40
    }

    @Test
    fun `part 1 result`() {
        day15.part1() shouldBe 592
    }

    @Test
    fun `part 2 test input`() {
        day15Test.part2() shouldBe 315
    }

    @Test
    @Timeout(value = 8_000, unit = TimeUnit.MILLISECONDS)
    fun `part 2 result`() {
        day15.part2() shouldBe 2897
    }

    @Test
    fun `part 2 result as grid`() {
        val pathInGridAsString = File("input/day15.txt").readLines().toMutableIntGrid(Char::digitToInt).pathInGridAsString()
        println(pathInGridAsString)
        pathInGridAsString.shouldNotBeBlank()
    }

    private fun Array<IntArray>.pathInGridAsString(): String {
        val graph = toWeightedGraph(listOf(-1 by 0, -1 by 0, 0 by 1, 1 by 0), computeValue = { it })
        val startPoint = 0 by 0
        val endPoint = first().lastIndex by lastIndex
        val start = graph[startPoint] ?: error("No start found at $startPoint")
        val goal = graph[endPoint] ?: error("No goal found at $endPoint")
        val grid = toMutableGrid(Int::toString)
        ((start dijkstra goal).leastCostPath + goal)
            .mapNotNull(WeightedNode<GridPoint2D>::value)
            .forEach { (x, y) -> grid[y][x] = "%2s".format(grid[y][x]).withColors(BRIGHT_YELLOW, YELLOW_BG) }

        return grid.gridAsString { if (it.length == 1) "%2s".format(it).withColors(random16BitColor(Random), BROWN_BG) else it }
    }

    @Nested
    inner class ElizarovTests {

        @Test
        fun `part 1 result Elizarov`() {
            day15.part1Elizarov() shouldBe 592
        }

        @Test
        fun `part 2 result Elizarov`() {
            day15.part2Elizarov().also { it.traceBackToStart { it.position }.reversed() }.cost shouldBe 2897
        }
    }

}

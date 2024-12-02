package aoc.seastories

import aoc.utils.*
import aoc.utils.model.GridPoint2D
import aoc.utils.model.GridPoint2D.Companion.by
import aoc.utils.model.WeightedNode
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldNotBeBlank
import java.io.File
import java.util.concurrent.TimeUnit
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Timeout
import kotlin.random.Random

internal class Day15ChitonTest {

    @Test
    fun `part 1 test input`() {
        Day15Chiton("input/day15test.txt").part1() shouldBe 40
    }

    @Test
    fun `part 1 result`() {
        Day15Chiton("input/day15.txt").part1() shouldBe 592
    }

    @Test
    fun `part 2 test input`() {
        Day15Chiton("input/day15test.txt").part2() shouldBe 315
    }

    @Test
    @Timeout(value = 8_000, unit = TimeUnit.MILLISECONDS)
    fun `part 2 result`() {
        Day15Chiton("input/day15.txt").part2() shouldBe 2897
    }

    @Test
    fun `part 2 result as grid`() {
        val pathInGridAsString = File("input/day15-2.txt").readLines().toIntGrid(Char::digitToInt).pathInGridAsString()
        println(pathInGridAsString)
        pathInGridAsString.shouldNotBeBlank()
    }

    private fun Array<IntArray>.pathInGridAsString(): String {
        val graph = toWeightedGraph(listOf(-1 by 0, -1 by 0, 0 by 1, 1 by 0), computeValue = { x, y -> x by y })
        val startPoint = 0 by 0
        val endPoint = first().lastIndex by lastIndex
        val start = graph[startPoint] ?: error("No start found at $startPoint")
        val goal = graph[endPoint] ?: error("No goal found at $endPoint")
        val grid = toGridOf(Int::toString)
        ((start dijkstra goal).leastCostPath + goal)
            .mapNotNull(WeightedNode<GridPoint2D>::value)
            .forEach { (x, y) -> grid[y][x] = "%2s".format(grid[y][x]).withColors(BRIGHT_YELLOW, YELLOW_BG) }

        return grid.gridAsString { if (it.length == 1) "%2s".format(it).withColors(random16BitColor(Random), BROWN_BG) else it }
    }

}

package aoc.seastories

import aoc.utils.*
import aoc.utils.model.GridPoint2D
import aoc.utils.model.GridPoint2D.Companion.by
import aoc.utils.model.WeightedNode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Timeout
import java.io.File
import java.util.concurrent.TimeUnit
import kotlin.test.assertTrue

internal class Day15ChitonTest {

    @Test
    fun `part 1 test input`() = assertEquals(40, Day15Chiton.part1("input/day15test.txt"))

    @Test
    fun `part 1 result`() = assertEquals(592, Day15Chiton.part1().also(::println))

    @Test
    fun `part 2 test input`() = assertEquals(315, Day15Chiton.part2("input/day15test.txt"))

    @Test
    @Timeout(value = 8_000, unit = TimeUnit.MILLISECONDS)
    fun `part 2 result`() = assertEquals(2897, Day15Chiton.part2().also(::println))

    @Test
    fun `part 2 result as grid`() {
        val pathInGridAsString = File("input/day15-2.txt").readLines().toIntGrid(Char::digitToInt).pathInGridAsString()
        println(pathInGridAsString)
        assertTrue(pathInGridAsString.isNotBlank())
    }

    private fun Array<IntArray>.pathInGridAsString(): String {
        val graph = toWeightedGraph(listOf(-1 by 0, -1 by 0, 0 by 1, 1 by 0), computeValue = { x, y -> x by y })
        val startPoint = 0 by 0
        val endPoint = first().lastIndex by lastIndex
        val start = graph[startPoint]!!
        val goal = graph[endPoint]!!
        val grid = toGridOf(Int::toString)
        start.dijkstra(goal).shortestPath.plus(goal)
            .mapNotNull(WeightedNode<GridPoint2D>::value)
            .forEach { (x, y) -> grid[y][x] = grid[y][x].withColors(BRIGHT_YELLOW, YELLOW_BG, 2) }

        return grid.gridAsString { if (it.length == 1) it.withColors(BROWN_BG, random16BitColor(), 2) else it }
    }

}

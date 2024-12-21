package aoc.seastories

import aoc.utils.*
import aoc.utils.grid2d.GridPoint2D
import aoc.utils.grid2d.GridPoint2D.Companion.by
import aoc.utils.graph.WeightedNode
import aoc.utils.graph.dijkstra
import aoc.utils.graph.toWeightedGraph
import aoc.utils.grid2d.forEachPointAndValue
import aoc.utils.grid2d.toMutableIntGrid
import java.io.File

internal class Day15Chiton(inputPath: String) : ChallengeDay {

    private val grid = File(inputPath).readLines()

    /**
     * What is the lowest total risk of any path from the top left to the bottom right?
     */
    override fun part1(): Int = grid.toMutableIntGrid(Char::digitToInt).calculateTotalRisk<Any>()

    /**
     * what is the lowest total risk of any path from the top left to the bottom right?
     */
    override fun part2() = grid
        .toMutableIntGrid(Char::digitToInt)
        .enlarge(times = 5)
        .calculateTotalRisk<Any>()

    private fun <T> Array<IntArray>.calculateTotalRisk(): Int =
        toShortestPath<T>(first().lastIndex by lastIndex, 0 by 0).sumOf(WeightedNode<T>::weight)

    private fun <T> Array<IntArray>.toShortestPath(
        startPoint: GridPoint2D,
        endPoint: GridPoint2D
    ): List<WeightedNode<T>> {
        val graph = toWeightedGraph<T>(listOf(1 by 0, 0 by 1, -1 by 0, 0 by -1))
        val start = graph[startPoint] ?: error("Start at $startPoint not found")
        val goal = graph[endPoint] ?: error("Goal at $endPoint not found")
        return start.dijkstra(goal).leastCostPath
    }

    private fun Array<IntArray>.enlarge(times: Int = 2): Array<IntArray> {
        val enlarged = Array(size * times) { IntArray(first().size * times) }
        for (stepX in 0 until times) {
            for (stepY in 0 until times) {
                toMutableIntGrid { (it + stepX + stepY).wrapBack(1, 9) }
                    .forEachPointAndValue { x, y, value ->
                        enlarged[y + size * stepY][x + first().size * stepX] = value
                    }
            }
        }
        return enlarged
    }

    /**
     * Added for comparison
     */
    fun part1Elizarov() = day15Part1(grid)

    /**
     * Added for comparison
     */
    fun part2Elizarov() = day15Part2(grid)
}

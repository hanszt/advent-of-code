package aoc.seastories

import aoc.utils.*
import aoc.utils.model.GridPoint2D
import aoc.utils.model.GridPoint2D.Companion.by
import aoc.utils.model.WeightedNode
import java.io.File
import java.util.*

internal class Day15Chiton(private val inputPath: String) : ChallengeDay {

    override fun part1(): Int = File(inputPath).readLines().toIntGrid(Char::digitToInt).calculateTotalRisk<Any>()

    override fun part2() = File(inputPath).readLines()
        .toIntGrid(Char::digitToInt)
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
                toIntGridOf { (it + stepX + stepY).wrapBack(1, 9) }
                    .forEachPointAndValue { x, y, value ->
                        enlarged[y + size * stepY][x + first().size * stepX] = value
                    }
            }
        }
        return enlarged
    }
}

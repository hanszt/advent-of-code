package aoc.jungle.adventures

import aoc.utils.ChallengeDay
import aoc.utils.invoke
import aoc.utils.model.GridPoint2D
import aoc.utils.model.GridPoint2D.Companion.by
import java.io.File
import aoc.utils.model.GridPoint2D as Point

/**
 * Credits to the Turkey dev.
 *
 * @see <a href="Day 12">https://github.com/TheTurkeyDev/Advent-of-Code-2022/blob/main/src/dev/theturkey/aoc22/Day12.java</a>
 * @see <a href="https://adventofcode.com/2022/day/12">Day 12: Hill Climbing Algorithm</a>
 */
class Day12HillClimbingAlgorithm(fileName: String) : ChallengeDay {

    private val lines = File(fileName).readLines()

    private fun createGrid(input: List<String>): Grid {
        val grid: MutableMap<Point, Int> = HashMap()
        lateinit var start: Point
        lateinit var end: Point
        for (y in input.indices) {
            val row = input[y]
            for (x in row.indices) {
                val point = y by x
                var height = row[x]
                if (height == 'S') {
                    start = point
                    height = 'a'
                }
                if (height == 'E') {
                    end = point
                    height = 'z'
                }
                grid[point] = height.code - 'a'.code
            }
        }
        return Grid(grid, start, end)
    }

    /**
     * Also a form of Breadth first search
     */
    private fun Grid.floodFill(postProcess: (Int, Int) -> (Int) = { _, length -> length }): Int {
        val shortestPaths = mutableMapOf(start to 0)
        val queue = ArrayDeque<Point>().apply { add(start) }
        while (queue.isNotEmpty()) {
            val pos = queue.removeFirst()
            for (dir in GridPoint2D.towerDirs) {
                val neighborPos = pos + dir
                grid[neighborPos]?.let { gridHeight ->
                    if (gridHeight - grid(pos) <= 1) {
                        val newPathLength = shortestPaths(pos) + 1
                        val pathLength = shortestPaths[neighborPos] ?: Int.MAX_VALUE
                        if (newPathLength < pathLength) {
                            shortestPaths[neighborPos] = postProcess(gridHeight, newPathLength)
                            queue.add(neighborPos)
                        }
                    }
                }
            }
        }
        return shortestPaths[goal] ?: error("No path found...")
    }

    override fun part1(): Int = createGrid(lines).floodFill()
    override fun part2(): Int = createGrid(lines).floodFill { height, length -> if (height == 0) 0 else length }

    data class Grid(val grid: Map<Point, Int>, val start: Point, val goal: Point)
}

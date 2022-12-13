package aoc

import aoc.utils.model.GridPoint2D.Companion.by
import java.io.File
import java.util.LinkedList
import java.util.Queue
import aoc.utils.model.GridPoint2D as Point

/**
 * Credits to the Turkey dev.
 *
 * @see <a href="Day 12">https://github.com/TheTurkeyDev/Advent-of-Code-2022/blob/main/src/dev/theturkey/aoc22/Day12.java</a>
 * @see <a href="https://adventofcode.com/2022/day/12">Day 12: Hill Climbing Algorithm</a>
 */
class Day12HillClimbingAlgorithm(fileName: String) : ChallengeDay {

    private val lines = File(fileName).readLines()

    private fun createGrid(input: List<String>): Triple<Point, Point, Map<Point, Int>> {
        val grid: MutableMap<Point, Int> = HashMap()
        var start: Point? = null
        var end: Point? = null
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
        return Triple(start!!, end!!, grid)
    }

    private fun Map<Point, Int>.floodFill(
        start: Point,
        goal: Point,
        postProcess: (Int, Int) -> (Int) = { _, length -> length }
    ): Int {
        val dirs = listOf(-1 by 0, 1 by 0, 0 by -1, 0 by 1)
        val shortestPaths = mutableMapOf(start to 0)
        val queue: Queue<Point> = LinkedList<Point>().apply { add(start) }
        while (queue.isNotEmpty()) {
            val pos = queue.remove()
            for (dir in dirs) {
                val neighborPos = pos + dir
                this[neighborPos]?.let {
                    val gridHeight = this[neighborPos]!!
                    if (gridHeight - this[pos]!! <= 1) {
                        val newPathLength = shortestPaths[pos]!! + 1
                        if (newPathLength < (shortestPaths[neighborPos] ?: Int.MAX_VALUE)) {
                            shortestPaths[neighborPos] = postProcess(gridHeight, newPathLength)
                            queue.add(neighborPos)
                        }
                    }
                }
            }
        }
        return shortestPaths[goal] ?: throw IllegalStateException("No path found...")
    }

    override fun part1(): Int {
        val (start, end, grid) = createGrid(lines)
        return grid.floodFill(start, end)
    }

    override fun part2(): Int {
        val (start, end, grid) = createGrid(lines)
        return grid.floodFill(start, end) { height, pathLength -> if (height == 0) 0 else pathLength }
    }
}

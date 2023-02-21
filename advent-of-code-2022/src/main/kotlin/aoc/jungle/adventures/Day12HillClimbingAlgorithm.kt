package aoc.jungle.adventures

import aoc.utils.ChallengeDay
import aoc.utils.model.GridPoint2D
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

    private fun Grid.floodFill(postProcess: (Int, Int) -> (Int) = { _, length -> length }): Int {
        val shortestPaths = mutableMapOf(start to 0)
        val queue: Queue<Point> = LinkedList<Point>().apply { add(start) }
        while (queue.isNotEmpty()) {
            val pos = queue.remove()
            for (dir in GridPoint2D.orthoDirs) {
                val neighborPos = pos + dir
                shortestPaths.update(pos, neighborPos, postProcess)?.also(queue::add)
            }
        }
        return shortestPaths[goal] ?: error("No path found...")
    }

    override fun part1(): Int = createGrid(lines).floodFill()
    override fun part2(): Int = createGrid(lines).floodFill { height, length -> if (height == 0) 0 else length }

    data class Grid(val grid: Map<Point, Int>, val start: Point, val goal: Point) {

        fun MutableMap<Point, Int>.update(pos: Point, neighborPos: Point, postProcess: (Int, Int) -> Int): Point? {
            grid[neighborPos]?.let {
                val gridHeight = grid[neighborPos]!!
                if (gridHeight - grid[pos]!! <= 1) {
                    val newPathLength = this[pos]!! + 1
                    if (newPathLength < (this[neighborPos] ?: Int.MAX_VALUE)) {
                        this[neighborPos] = postProcess(gridHeight, newPathLength)
                        return neighborPos
                    }
                }
            }
            return null
        }
    }
}

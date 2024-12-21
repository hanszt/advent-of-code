package aoc.historianhysteria

import aoc.utils.*
import aoc.utils.grid2d.Dimension2D
import aoc.utils.grid2d.GridPoint2D
import aoc.utils.grid2d.dimension2D
import aoc.utils.grid2d.get
import aoc.utils.grid2d.getOrNull
import aoc.utils.grid2d.gridPoint2D
import aoc.utils.grid2d.rangeTo
import aoc.utils.grid2d.set
import aoc.utils.grid2d.toMutableCharGrid
import java.nio.file.Path
import kotlin.io.path.readLines

class Day18(
    private val input: List<String>,
    private val nrOfFallenBytes: Int = 1_024,
    private val dimension2D: Dimension2D = dimension2D(71, 71)
) : ChallengeDay {

    constructor(path: Path) : this(path.readLines())

    /**
     * Simulate the first kilobyte (1024 bytes) falling onto your memory space.
     * Afterward, what is the minimum number of steps needed to reach the exit?
     */
    override fun part1(): Int {
        val memoryGrid = dimension2D.toMutableCharGrid { '.' }.apply {
            input.take(nrOfFallenBytes).forEach {
                val point = it.split(',')
                    .let { (x, y) -> gridPoint2D(x.toInt(), y.toInt()) }
                this[point] = '#'
            }
        }

        val start = dimension2D.start
        val shortestPaths = mutableMapOf(start to 0)
        // Bfs
        val queue = ArrayDeque<GridPoint2D>().apply { add(start) }
        val visited = HashSet<GridPoint2D>()
        while (queue.isNotEmpty()) {
            val pos = queue.removeFirst()
            for (d in GridPoint2D.towerDirs) {
                val neighborPos = pos + d
                val neighbor = memoryGrid.getOrNull(neighborPos)
                if (neighbor == '.') {
                    val newPathLength = shortestPaths(pos) + 1
                    val pathLength = shortestPaths[neighborPos] ?: Int.MAX_VALUE
                    if (neighborPos !in visited) {
                        if (newPathLength < pathLength) {
                            shortestPaths[neighborPos] = newPathLength
                            queue.add(neighborPos)
                        }
                    }
                }
            }
        }
        return shortestPaths[dimension2D.endExclusive] ?: error("No shortest path found")
    }

    /**
     * What are the coordinates of the first byte that will prevent the exit from being reachable from your starting position?
     *
     * My own slow, but working solution.
     *
     * The idea is to check for each extra fallen byte if a closed `wall` of bytes can be made that separates the upper left corner form the lower right.
     */
    override fun part2(): GridPoint2D {
        val lastX = dimension2D.width - 1
        val lastY = dimension2D.height - 1
        val horTopRange = gridPoint2D(1, 0)..gridPoint2D(dimension2D.width - 2, 0)
        val horBottomRange = gridPoint2D(1, lastY)..gridPoint2D(dimension2D.width - 2, lastY)
        val leftRange = gridPoint2D(0, 1)..gridPoint2D(0, dimension2D.height - 2)
        val rightRange = gridPoint2D(lastX, 1)..gridPoint2D(lastX, dimension2D.height - 2)
        val startsToEnds = listOf(horBottomRange to rightRange, leftRange to horTopRange)

        for (fallenBytes in nrOfFallenBytes..input.size) {
            val memoryGrid = dimension2D.toMutableCharGrid { '.' }
            val bytePositions = input.take(fallenBytes).map {
                it.split(',').let { (x, y) -> gridPoint2D(x.toInt(), y.toInt()) }
            }
            bytePositions.forEach { memoryGrid[it] = '#' }

            for ((startRange, endRange) in startsToEnds) {
                for (start in startRange) {
                    if (memoryGrid[start] == '#') {
                        // Dfs
                        val stack = ArrayDeque<GridPoint2D>().apply { add(start) }
                        val visited = HashSet<GridPoint2D>()
                        while (stack.isNotEmpty()) {
                            val pos = stack.removeLast()
                            if (pos in endRange) {
                                return bytePositions.last()
                            }
                            visited.add(pos)
                            for (d in GridPoint2D.kingDirs) {
                                val neighborPos = pos + d
                                val neighbor = memoryGrid.getOrNull(neighborPos)
                                if (neighbor == '#') {
                                    if (neighborPos !in visited) {
                                        stack += neighborPos
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        error("Not found")
    }

    fun part1Elizarov() = day18Part1(input, dimension2D, nrOfFallenBytes)
    fun part2Elizarov() = day18Part2(input, dimension2D)
    fun part2ElizarovRaw() = day18Part2Raw(input)
}

package aoc.jungle.adventures

import aoc.utils.ChallengeDay
import aoc.utils.grid2d.GridPoint2D
import aoc.utils.grid2d.GridPoint2D.Companion.by
import aoc.utils.grid2d.GridPoint2D.Companion.east
import aoc.utils.grid2d.GridPoint2D.Companion.north
import aoc.utils.grid2d.GridPoint2D.Companion.south
import aoc.utils.grid2d.GridPoint2D.Companion.west
import aoc.utils.grid2d.gridPoint2D
import aoc.utils.toSetOf
import java.io.File

/**
 * Credits to Elizarov
 *
 * @see <a href="https://adventofcode.com/2022/day/24">Day 24</a>
 */
class Day24BlizzardBasin(fileName: String) : ChallengeDay {

    private val input = File(fileName).readLines()
    override fun part1() = day24Part(1)
    override fun part2() = day24Part(2)

    private val dirs = listOf(south, east, north, west)

    private fun day24Part(part: Int): Int {
        val grid = Array(input.size) { input[it].toCharArray() }
        val width = grid.size
        val height = grid[0].size

        val blizzards = blizzards(grid)
        var start = 0 by 1
        var finish = width - 1 by height - 2
        var currentPoints = mutableSetOf(start)
        val totalPhases = if (part == 1) 0 else 2
        var phase = 0
        var minutes = 0
        while (phase < totalPhases || finish !in currentPoints) {
            if (finish in currentPoints) {
                phase++
                currentPoints.clear()
                finish = start.also { start = finish }
                currentPoints += start
            }
            val nextBlizzard = blizzards.toSetOf { blizzard ->
                val dir = dirs[blizzard.state]
                val (nx, ny) = blizzard.plus(dir.times(minutes + 1)).minus(1 by 1)
                gridPoint2D(nx.mod(width - 2) + 1, ny.mod(height - 2) + 1)
            }

            currentPoints = nextPoints(currentPoints, nextBlizzard) {
                this !in nextBlizzard &&
                        (x in 0 until width &&
                                y in 0 until height &&
                                grid[x][y] != '#')
            }
            minutes++
        }
        return minutes
    }

    private fun nextPoints(
        currentPoints: Set<GridPoint2D>,
        nextBlizzard: Set<GridPoint2D>,
        shouldBeAdded: GridPoint2D.() -> Boolean
    ): MutableSet<GridPoint2D> {
        val nextPoints = mutableSetOf<GridPoint2D>()
        for (point in currentPoints) {
            for (delta in dirs) {
                val neighbor = point.plus(delta)
                if (neighbor.shouldBeAdded()) {
                    nextPoints += neighbor
                }
            }
            if (point !in nextBlizzard) nextPoints += point
        }
        return nextPoints
    }

    private inline fun <R> Array<CharArray>.mapIndexed2NotNull(transform: (x: Int, y: Int, c: Char) -> R?): List<R> =
        buildList {
            for (x in this@mapIndexed2NotNull.indices) {
                val row = this@mapIndexed2NotNull[x]
                for (y in row.indices) {
                    transform(x, y, row[y])?.let(::add)
                }
            }
        }

    data class Blizzard(override val x: Int, override val y: Int, val state: Int) : GridPoint2D

    private fun blizzards(grid: Array<CharArray>) = grid.mapIndexed2NotNull { x, y, c ->
        val state = when (c) {
            '>' -> 0
            'v' -> 1
            '<' -> 2
            '^' -> 3
            '#', '.' -> -1
            else -> error(grid[x][y])
        }
        if (state >= 0) Blizzard(x, y, state) else null
    }
}

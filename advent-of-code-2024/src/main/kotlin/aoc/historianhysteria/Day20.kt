package aoc.historianhysteria

import aoc.utils.ChallengeDay
import aoc.utils.grid2d.*
import java.nio.file.Path
import kotlin.io.path.readLines

class Day20(private val input: List<String>, private val targetMinSaveTime: Int = 100) : ChallengeDay {

    constructor(path: Path) : this(path.readLines())

    /**
     * How many cheats would save you at least 100 picoseconds?
     */
    override fun part1(): Int {
        val start = input.firstPoint { it == 'S' }
        val target = input.firstPoint { it == 'E' }
        val grid = input.toMutableCharGrid()
        val initial = findShortestTime(grid, start, target)
        var count = 0
            grid.forEachPoint { p ->
                if (isCandidate(grid, p)) {
                    grid[p] = '.'
                    val shortestTime = findShortestTime(grid, start, target)
                    grid[p] = '#'
                    val savedTime = initial - shortestTime
                    if (targetMinSaveTime <= savedTime) count++
                }
        }
        return count
    }

    override fun part2(): Int = day20Part2(input)

    private fun isCandidate(grid: Array<CharArray>, p: GridPoint2D): Boolean = grid[p] == '#' &&
            ((grid.getOrNull(p.plusX(-1)).isPath() && grid.getOrNull(p.plusX(1)).isPath()) ||
                    (grid.getOrNull(p.plusY(-1)).isPath() && grid.getOrNull(p.plusY(1)).isPath()))

    private fun findShortestTime(grid: Array<CharArray>, start: GridPoint2D, target: GridPoint2D): Int {
        val s = Grid2DNode(start)
        val nodes = HashMap<GridPoint2D, Grid2DNode>().apply { put(start, s) }
        val q = ArrayDeque<Grid2DNode>().apply { add(s) }
        while (q.isNotEmpty()) {
            val cur = q.removeFirst()
            if (cur.position == target) return cur.cost
            val newT = cur.cost + 1
            for (nd in GridPoint2D.orthoDirs) {
                val n = cur.position + nd
                val nc = grid.getOrNull(n)
                if (nc.isPath()) {
                    val t = nodes[n]?.cost
                    if (t == null || newT < t) {
                        val node = Grid2DNode(n, newT, cur)
                        nodes[n] = node
                        q += node
                    }
                }
            }
        }
        error("Not found")
    }

    private fun Char?.isPath(): Boolean = this == '.' || this == 'E' || this == 'S'

    fun part1Elizarov() = day20Part1(input)
}

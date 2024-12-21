package aoc.historianhysteria

import aoc.utils.*
import aoc.utils.grid2d.MutableCharGrid
import aoc.utils.grid2d.GridPoint2D
import aoc.utils.grid2d.findPoint
import aoc.utils.grid2d.forEachPoint
import aoc.utils.grid2d.get
import aoc.utils.grid2d.getOrNull
import aoc.utils.grid2d.set
import aoc.utils.grid2d.toMutableCharGrid
import java.nio.file.Path
import kotlin.io.path.readLines


class Day20(private val input: List<String>, private val targetMinSaveTime: Int = 100) : ChallengeDay {


    constructor(path: Path) : this(path.readLines())

    /**
     * How many cheats would save you at least 100 picoseconds?
     */
    override fun part1(): Int {
        val start = input.findPoint { it == 'S' } ?: error("Start not found")
        val target = input.findPoint { it == 'E' } ?: error("Target not found")
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

    private fun isCandidate(grid: MutableCharGrid, p: GridPoint2D): Boolean = grid[p] == '#' &&
            ((grid.getOrNull(p.plusX(-1)).isPath() && grid.getOrNull(p.plusX(1)).isPath()) ||
                    (grid.getOrNull(p.plusY(-1)).isPath() && grid.getOrNull(p.plusY(1)).isPath()))

    private fun findShortestTime(grid: MutableCharGrid, start: GridPoint2D, target: GridPoint2D): Int {
        data class Node(val p: GridPoint2D, val time: Int = 0, val prev: Node? = null)
        val s = Node(start)
        val nodes = mutableMapOf<GridPoint2D, Node>().apply { put(start, s) }
        val q = ArrayDeque<Node>().apply { add(s) }
        while (q.isNotEmpty()) {
            val cur = q.removeFirst()
            if (cur.p == target) return cur.time
            val newT = cur.time + 1
            for (nd in GridPoint2D.orthoDirs) {
                val n = cur.p + nd
                val nc = grid.getOrNull(n)
                if (nc.isPath()) {
                    val t = nodes[n]?.time
                    if (t == null || newT < t) {
                        val node = Node(n, newT, cur)
                        nodes[n] = node
                        q += node
                    }
                }
            }
        }
        error("Not found")
    }

    fun part1Elizarov() = day20Part1(input)

    private fun Char?.isPath(): Boolean = this == '.' || this == 'E' || this == 'S'

    override fun part2(): Int = day20Part2(input)
}

package aoc.snowrescuemission

import aoc.utils.graph.Node
import aoc.utils.grid2d.get
import aoc.utils.grid2d.getOrNull
import aoc.utils.grid2d.lowerRight
import java.util.*
import aoc.utils.grid2d.GridPoint2D as P2

/**
 * Source: https://github.com/abnew123/aoc2023.git
 */
class Day17Abnew(private val grid: List<String>) {

    private val ulrd = listOf(P2.up, P2.left, P2.right, P2.down)

    internal fun part1(): Crucible = solve(min = 1, max = 3)
    internal fun part2(): Crucible = solve(min = 4, max = 10)

    private fun solve(min: Int, max: Int): Crucible {
        // u: all
        // l: l, r
        // r: u, d
        // d: u, d
        // d + 1: l, r
        val allowedTurns = listOf(
            intArrayOf(0, 1, 2, 3),
            intArrayOf(1, 2),
            intArrayOf(0, 3),
            intArrayOf(0, 3),
            intArrayOf(1, 2)
        )

        val state = Array(grid.size) {
            Array(grid[it].length) { IntArray(allowedTurns.size) { Int.MAX_VALUE } }
        }
        val queue = PriorityQueue(compareBy(Crucible::best))
        val visited = HashSet<Crucible>()
        val start = Crucible(P2.ZERO, 0, 0)
        queue.add(start)
        while (queue.isNotEmpty()) {
            val c = queue.poll()
            visited.add(c)
            if (c.position == grid.lowerRight) return c
            for (di in allowedTurns[c.dirIndex]) {
                var best = c.best
                for (i in 1..max) {
                    val np = c.position + ulrd[di] * i
                    best += grid.getOrNull(np)?.digitToInt() ?: break
                    // Check if better path
                    if (i >= min && best < state[np][di]) {
                        state[np][di] = best
                        val nc = Crucible(position = np, dirIndex = di + 1, best = best, prev = c)
                        if (nc !in visited) {
                            queue.add(nc)
                        }
                    }
                }
            }
        }
        error("Not found")
    }

    @JvmRecord
    internal data class Crucible(val position: P2, val dirIndex: Int, val best: Int, override val prev: Crucible? = null) : Node<Crucible> {
        override fun equals(other: Any?): Boolean = this === other || (other is Crucible && other.position == position && other.dirIndex == dirIndex)
        override fun hashCode(): Int = arrayOf(position, dirIndex).contentHashCode()
        override fun toString(): String = "Crucible(p=$position, direction=$dirIndex, best=$best)"

    }
}
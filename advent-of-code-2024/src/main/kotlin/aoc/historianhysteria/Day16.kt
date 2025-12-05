package aoc.historianhysteria

import aoc.utils.ChallengeDay
import aoc.utils.graph.Node
import aoc.utils.grid2d.GridPoint2D.Companion.orthoDirs
import aoc.utils.grid2d.firstPoint
import aoc.utils.grid2d.get
import java.nio.file.Path
import java.util.*
import kotlin.Int.Companion.MAX_VALUE
import kotlin.io.path.readLines
import kotlin.math.min
import aoc.utils.grid2d.GridPoint2D as P2

private const val TURING_COST = 1000

/**
 * [Adaption of elizarov 2024 day 16 solution](https://github.com/elizarov/AdventOfCode2024/blob/main/src/Day16_2.kt)
 *
 * [aoc.utils.Tag.PATH_SEARCH]
 */
class Day16(private val maze: List<String>) : ChallengeDay {
    constructor(path: Path) : this(path.readLines())

    private val m = maze.firstOrNull()?.length ?: 0
    private val n = maze.size

    /**
     * What is the lowest score a Reindeer could possibly get?
     */
    override fun part1(): Int = findShortestPath()

    /**
     * How many tiles are part of at least one of the best paths through the maze?
     */
    override fun part2(): Int {
        val cost = Array(m) { Array(n) { IntArray(orthoDirs.size) { MAX_VALUE } } }
        val best = findShortestPath(cost)

        val visited = HashSet<P2>()
        val q = ArrayDeque<Spot>()
        var tiles = 0
        fun enq(pos: P2, d: Int, c: Int) {
            if (cost[pos][d] != c) return
            if (visited.add(pos)) tiles++
            q += Spot(pos, d, c)
        }
        val goal = maze.firstPoint { it == 'E' }
        for (d in orthoDirs.indices) if (cost[goal][d] == best) enq(goal, d, best)
        while (q.isNotEmpty()) {
            val (pos, d, score) = q.removeFirst()
            enq(pos - orthoDirs[d], d, score - 1)
            enq(pos, leftDir(d), score - TURING_COST)
            enq(pos, rightDir(d), score - TURING_COST)
        }
        return tiles
    }

    private fun findShortestPath(
        cost: Array<Array<IntArray>> = Array(m) { Array(n) { IntArray(orthoDirs.size) { MAX_VALUE } } },
    ): Int {
        val q = PriorityQueue(compareBy(Spot::cost))
        val visited = Array(m) { Array(n) { BooleanArray(orthoDirs.size) } }
        fun enq(pos: P2, d: Int, c: Int, prev: Spot? = null) {
            if (cost[pos][d] <= c) return
            if (maze[pos] == '#') return
            cost[pos][d] = c
            q.add(Spot(pos, d, c, prev))
        }
        enq(maze.firstPoint { it == 'S' }, 0, 0)
        // Do a non short-circuiting search to satisfy the requirements for the cost matrix for part 2
        var best = MAX_VALUE
        while (q.isNotEmpty()) {
            val cur = q.remove()
            val (pos, d, score) = cur
            if (visited[pos][d]) continue
            visited[pos][d] = true
            if (maze[pos] == 'E') {
                best = min(score, best)
            }
            val dir = orthoDirs[d]
            enq(pos + dir, d, score + 1, cur)
            enq(pos, leftDir(d), score + TURING_COST, cur)
            enq(pos, rightDir(d), score + TURING_COST, cur)
        }
        return best
    }

    private fun leftDir(d: Int): Int = (d + 1) % 4
    private fun rightDir(d: Int): Int = (d + 3) % 4

    private data class Spot(val pos: P2, val dir: Int, val cost: Int, override val prev: Spot? = null) : Node<Spot>
}

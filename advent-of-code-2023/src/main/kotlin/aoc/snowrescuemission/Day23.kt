package aoc.snowrescuemission

import aoc.utils.ChallengeDay
import aoc.utils.graph.Node
import aoc.utils.grid2d.Grid2DNode
import aoc.utils.grid2d.GridPoint2D.Companion.orthoDirs
import aoc.utils.grid2d.get
import aoc.utils.grid2d.getOrNull
import aoc.utils.grid2d.lowerRight
import aoc.utils.invoke
import java.nio.file.Path
import kotlin.io.path.readLines
import aoc.utils.grid2d.GridPoint2D as P2

/**
 * Source: [Day 23](https://github.com/elizarov/AdventOfCode2023/blob/main/src/Day23.kt)
 *
 * [aoc.utils.Tag.PATH_SEARCH]
 */
class Day23(private val maze: List<String>) : ChallengeDay {
    constructor(path: Path) : this(path.readLines())

    private val day23Zebalu = Day23Zebalu(maze)

    /**
     * Considering the slippery slopes, how many steps long is the longest hike?
     */
    override fun part1(): Int = longestHike { p, d -> maze[p] != '.' && maze[p] != ">v<^"[d] }
    fun part1Zebalu() = day23Zebalu.part1()

    /**
     * How many steps long is the longest hike when there are no slippery slopes?
     */
    override fun part2(): Int = longestHike()
    fun part2Zebalu() = day23Zebalu.part2()

    private fun longestHike(isSlippery: (P2, Int) -> Boolean = { _, _ -> false }): Int {
        val adjList = buildGraph(isSlippery)
        val paths = traverse(adjList)
        val target = Pos(maze.lowerRight - P2(1, 0))
        return paths.findLongest(start = Pos(P2(1, 0)), target = target)
    }

    data class Pos(val p2: P2, override val prev: Pos? = null) : Node<Pos> {
        override fun equals(other: Any?): Boolean = this === other || (other is Pos && p2 == other.p2)
        override fun hashCode(): Int = p2.hashCode()
    }

    private fun Map<P2, List<Grid2DNode>>.findLongest(start: Pos, target: Pos): Int {
        val visited = HashSet<Pos>()
        fun find(p: Pos): Int {
            if (p == target) {
                return 0
            }
            visited += p
            var longest = -1
            for (step in this(p.p2)) {
                val nPos = Pos(step.position, p)
                if (nPos !in visited) {
                    val next = find(nPos)
                    if (next < 0) continue
                    longest = maxOf(longest, next + step.cost)
                }
            }
            visited -= p
            return longest
        }
        return find(start)
    }

    private fun traverse(adjList: Map<P2, List<P2>>): Map<P2, List<Grid2DNode>> = buildMap<P2, MutableList<Grid2DNode>> {
        for ((p, neighbors) in adjList) {
            if (neighbors.size != 2) {
                for (n in neighbors) {
                    var pos = p
                    var next = n
                    var dist = 1
                    while (true) {
                        val lc = (adjList[next]?.toSet() ?: emptySet()) - pos
                        pos = next
                        next = lc.singleOrNull() ?: break
                        dist++
                    }
                    getOrPut(p, ::ArrayList).add(Grid2DNode(position = next, cost = dist))
                }
            }
        }
    }

    private fun buildGraph(isSlippery: (P2, Int) -> Boolean): Map<P2, List<P2>> = buildMap<P2, MutableList<P2>> {
        for (y in 0..<maze.size) for (x in 0..<maze[y].length) {
            val p = P2(x, y)
            if (maze[p] == '#') continue
            for (di in orthoDirs.indices) {
                if (isSlippery(p, di)) continue
                val np = p + orthoDirs[di]
                maze.getOrNull(np)?.takeUnless { it == '#' } ?: continue
                getOrPut(p, ::ArrayList).add(np)
            }
        }
    }
}

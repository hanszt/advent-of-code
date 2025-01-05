package aoc.snowrescuemission

import aoc.utils.ChallengeDay
import aoc.utils.graph.Node
import aoc.utils.grid2d.get
import aoc.utils.grid2d.getOrNull
import aoc.utils.invoke
import java.nio.file.Path
import kotlin.collections.toSet
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
        val initGraph = buildGraph(isSlippery)
        val graph = traverse(initGraph)

        val start = P2(1, 0)
        val target = P2(maze[0].length - 2, maze.size - 1)

        return graph.findLongest(start, target)
    }

    private fun Map<P2, List<E2>>.findLongest(start: P2, target: P2): Int {
        val visited = HashSet<P2>()
        fun find(p: P2): Int {
            if (p == target) return 0
            visited += p
            var res = -1
            for (e in this(p)) if (e.v !in visited) {
                val next = find(e.v)
                if (next < 0) continue
                res = maxOf(res, next + e.w)
            }
            visited -= p
            return res
        }
        return find(start)
    }

    private fun traverse(g0: Map<P2, List<P2>>): Map<P2, List<E2>> = buildMap<P2, MutableList<E2>> {
        for (n in g0) {
            val (p0, l0) = n
            if (l0.size != 2) {
                for (p1 in l0) {
                    var pp = p0
                    var pc = p1
                    var w = 1
                    while (true) {
                        val lc = (g0[pc]?.toSet() ?: emptySet()) - setOf(pp)
                        pp = pc
                        pc = lc.singleOrNull() ?: break
                        w++
                    }
                    getOrPut(p0, ::ArrayList).add(E2(pc, w))
                }
            }
        }
    }

    private fun buildGraph(isSlippery: (P2, Int) -> Boolean): Map<P2, List<P2>> = buildMap<P2, MutableList<P2>> {
        for (y in 0..<maze.size) for (x in 0..<maze[y].length) {
            val p = P2(x, y)
            if (maze[p] == '#') continue
            for (d in 0..<P2.orthoDirs.size) {
                if (isSlippery(p, d)) continue
                val p1 = p + P2.orthoDirs[d]
                maze.getOrNull(p1)?.takeUnless { it == '#' } ?: continue
                getOrPut(p, ::ArrayList).add(p1)
            }
        }
    }

    data class E2(val v: P2, val w: Int, override val prev: E2? = null) : Node<E2> {
        override fun equals(other: Any?): Boolean  = this === other ||
                (other is E2 && v == other.v && w == other.w)
        override fun hashCode(): Int = arrayOf(v, w).contentHashCode()
    }
}

package aoc.historianhysteria

import aoc.utils.ChallengeDay
import aoc.utils.grid2d.*
import aoc.utils.grid2d.GridPoint2D.Companion.orthoDirs
import java.nio.file.Path
import kotlin.io.path.readLines
import aoc.utils.grid2d.GridPoint2D as P2

/**
 * Refactored versions of Elizarov's solutions
 *
 * [aoc.utils.Tag.FLOOD_FILL]
 */
class Day12(private val input: List<String>) : ChallengeDay {
    constructor(path: Path) : this(path.readLines())

    /**
     * What is the total price of fencing all regions on your map?
     */
    override fun part1(): Long = solve { areaPoints, isEdge ->
        var perimeter = 0L
        for (p in areaPoints) {
            for (n in p.orthoNeighbors()) {
                if (isEdge(n)) perimeter++
            }
        }
        perimeter * areaPoints.size
    }

    /**
     * What is the new total price of fencing all regions on your map?
     */
    override fun part2(): Long = solve { areaPoints, isEdge ->
        val g = List(orthoDirs.size) { ArrayList<P2>() }
        for (p in areaPoints) {
            for (k in orthoDirs.indices) {
                val n = p + orthoDirs[k]
                if (isEdge(n)) g[k] += n
            }
        }
        var nrOfSides = 0L
        for (k in orthoDirs.indices) {
            val gr: Map<Int, List<P2>>
            val selector: (P2) -> Int
            if (orthoDirs[(k + 1) % orthoDirs.size].x == 0) {
                gr = g[k].groupBy { it.x }
                selector = P2::y
            } else {
                gr = g[k].groupBy { it.y }
                selector = P2::x
            }
            for (row in gr.values) {
                var prev = Int.MIN_VALUE
                for (pt in row.sortedBy(selector)) {
                    val cur = selector(pt)
                    if (cur > prev + 1) nrOfSides++
                    prev = cur
                }
            }
        }
        nrOfSides * areaPoints.size
    }

    private fun solve(calculateCost: (Collection<P2>, predicate: (P2) -> Boolean) -> Long): Long {
        val f = Array(input.size) { IntArray(input[it].length) }
        val q = ArrayDeque<P2>()
        var count = 0
        fun enq(p: P2, c: Char) {
            val ch = input.getOrNull(p) ?: return
            if (f[p] != 0 || ch != c) return
            f[p] = count
            q += p
        }

        var sum = 0L
        input.forEachPoint { p ->
            if (f[p] == 0) {
                count++
                q.clear()
                val c = input[p]
                enq(p, c)
                var h = 0
                while (h < q.size) {
                    val p = q[h++]
                    for (n in p.orthoNeighbors()) {
                        enq(n, c)
                    }
                }
                sum += calculateCost(q) { n -> f.getOrNull(n)?.takeIf { it == count } == null }
            }
        }
        return sum
    }

}

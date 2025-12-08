package aoc.secret_entrance

import aoc.utils.ChallengeDay
import aoc.utils.grid3d.GridPoint3D
import java.nio.file.Path
import kotlin.io.path.readLines

/**
 * [aoc.utils.Tag.THREE_D], [aoc.utils.Tag.GRAPH]
 */
class Day08(lines: List<String>, private val limit: Int = 1_000) : ChallengeDay {
    constructor(path: Path) : this(path.readLines())

    private val coords = lines.map {
        it.split(",").map(String::toInt).let { (x, y, z) -> GridPoint3D(x, y, z) }
    }
    private val n = coords.size

    /**
     * [Solution Elizarov](https://github.com/elizarov/AdventOfCode2025/blob/main/src/Day08_1.kt)
     */
    override fun part1(): Long {
        val distances = distancesAscending()

        val g = List(n) { ArrayList<Int>() }
        for ((i0, j0) in distances.take(limit)) {
            g[i0] += j0
            g[j0] += i0
        }
        // Connected components
        val cIdx = IntArray(n)
        val cSize = IntArray(n + 1)
        var cCnt = 0
        val q = IntArray(n)
        for (i0 in 0..<n) if (cIdx[i0] == 0) {
            cCnt++
            cIdx[i0] = cCnt
            cSize[cCnt] = 1
            q[0] = i0
            var qh = 0
            var qt = 1
            while (qh < qt) {
                val i = q[qh++]
                for (j in g[i]) if (cIdx[j] == 0) {
                    cIdx[j] = cCnt
                    cSize[cCnt]++
                    q[qt++] = j
                }
            }
        }
        return cSize.sortedDescending().take(3).fold(1L) { a, b -> a * b }
    }

    private fun distancesAscending(): List<Dist> = buildList {
        for (i in coords.indices) for (j in i + 1..<n) {
            this += Dist(i, j, coords[i].distanceSquared(coords[j]))
        }
        sortBy { it.distance }
    }

    override fun part2(): Int {
        val distances = distancesAscending()
        val g = List(n) { ArrayList<Int>() }
        // Disjoint-Set Union (DSU) algorithm. https://cp-algorithms.com/data_structures/disjoint_set_union.html
        val dUp = IntArray(n) { it }
        val dSz = IntArray(n) { 1 }
        var num = n
        for ((i0, j0) in distances) {
            var ci = i0
            while (dUp[ci] != ci) ci = dUp[ci]
            var cj = j0
            while (dUp[cj] != cj) cj = dUp[cj]
            if (ci == cj) continue
            if (dSz[ci] < dSz[cj]) {
                dUp[ci] = cj
                dSz[cj] += dSz[ci]
            } else {
                dUp[cj] = ci
                dSz[ci] += dSz[cj]
            }
            g[i0] += j0
            g[j0] += i0
            num--
            if (num == 1) {
                println("g = ${g}")
                return coords[i0].x * coords[j0].x
            }
        }
        error("No solution found!")
    }

    data class Dist(val i: Int, val j: Int, val distance: Long)
}

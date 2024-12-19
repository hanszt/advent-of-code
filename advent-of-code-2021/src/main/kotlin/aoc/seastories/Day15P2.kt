package aoc.seastories

import aoc.utils.get
import aoc.utils.getOrNull
import aoc.utils.model.GridPoint2D
import aoc.utils.set
import aoc.utils.toIntGrid
import java.util.*

internal fun day15Part1(input: List<String>): Int {
    val a = input.toIntGrid { it.digitToInt() }
    val m = a[0].size
    val n = a.size
    val d = Array(n) { IntArray(m) { Int.MAX_VALUE } }
    val v = Array(n) { BooleanArray(m) }
    fun relax(i: Int, j: Int, x: Int) {
        if (i !in 0 until n || j !in 0 until m) return
        d[i][j] = minOf(d[i][j], x + a[i][j])
    }
    d[0][0] = 0
    while (!v[n - 1][m - 1]) {
        var mx = Int.MAX_VALUE
        var mi = -1
        var mj = -1
        for (i in 0 until n) for (j in 0 until m) {
            if (!v[i][j] && d[i][j] < mx) {
                mx = d[i][j]
                mi = i
                mj = j
            }
        }
        v[mi][mj] = true
        relax(mi - 1, mj, mx)
        relax(mi + 1, mj, mx)
        relax(mi, mj - 1, mx)
        relax(mi, mj + 1, mx)
    }
    return d[n - 1][m - 1]
}

/**
 * https://github.com/elizarov/AdventOfCode2021/blob/main/src/Day15_2_fast.kt
 */
internal fun day15Part2(input: List<String>): Int {
    val a0 = input.toIntGrid { it.digitToInt() }
    val n0 = a0.size
    val m0 = a0[0].size
    val n = 5 * n0
    val m = 5 * m0
    val enlarged = Array(n) { i ->
        IntArray(m) { j ->
            val k = i / n0 + j / m0
            (a0[i % n0][j % m0] + k - 1) % 9 + 1
        }
    }
    val d = Array(n) { IntArray(m) { Int.MAX_VALUE } }

    data class Pos(override val x: Int, override val y: Int, val dist: Int) : GridPoint2D {
        override fun plus(other: GridPoint2D): Pos = Pos(x + other.x, y + other.y, dist)
    }

    val visited = Array(n) { BooleanArray(m) }
    val q = PriorityQueue(compareBy(Pos::dist))

    d[0][0] = 0
    q.add(Pos(0, 0, 0))
    while (!visited[n - 1][m - 1]) {
        val p = q.remove()
        if (visited[p]) continue
        visited[p] = true
        for (dir in GridPoint2D.orthoDirs) {
            val neighbor = p + dir
            enlarged.getOrNull(neighbor)?.let { dist ->
                val newDistance = neighbor.dist + dist
                if (newDistance < d[neighbor]) {
                    d[neighbor] = newDistance
                    q += neighbor.copy(dist = newDistance)
                }
            }
        }
    }
    return d[n - 1][m - 1]
}
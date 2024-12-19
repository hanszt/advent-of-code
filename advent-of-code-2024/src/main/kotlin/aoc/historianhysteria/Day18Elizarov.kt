package aoc.historianhysteria

import aoc.utils.*
import aoc.utils.model.*

/**
 * https://github.com/elizarov/AdventOfCode2024/blob/main/src/Day18_1.kt
 */
fun day18Part1(input: List<String>, dimension2D: Dimension2D, nrOfFallenBytes: Int): Int {
    val (m, n) = dimension2D
    val a = input.map {
        it.split(',').let { (x, y) -> gridPoint2D(x.toInt(), y.toInt()) }
    }
    val c = Array(n) { BooleanArray(m) }
    for (p in a.take(nrOfFallenBytes)) c[p] = true

    val distances = Array(n) { IntArray(m) { Int.MAX_VALUE } }
    val q = ArrayList<GridPoint2D>()
    fun enq(p: GridPoint2D, d: Int) {
        val isByte = c.getOrNull(p) ?: return
        if (isByte) return
        if (distances[p] <= d) return
        distances[p] = d
        q += p
    }
    enq(GridPoint2D.ZERO, 0)
    while (q.isNotEmpty()) {
        val pos = q.removeFirst()
        val d = distances[pos]
        for (dir in GridPoint2D.orthoDirs) {
            enq(pos + dir, d + 1)
        }
    }
    return distances[distances.lowerRight]
}

/**
 * https://github.com/elizarov/AdventOfCode2024/blob/main/src/Day18_2.kt
 *
 * Refactored
 */
fun day18Part2(input: List<String>, dimension2D: Dimension2D): GridPoint2D {
    val (m, n) = dimension2D
    val a = input.map {
        it.split(',').let { (x, y) -> gridPoint2D(x.toInt(), y.toInt()) }
    }
    val c = Array(n) { BooleanArray(m) }
    for (p in a) {
        c[p] = true
        val distances = Array(n) { IntArray(m) { Int.MAX_VALUE } }
        val q = ArrayDeque<GridPoint2D>()
        fun enq(p: GridPoint2D, d: Int) {
            val isByte = c.getOrNull(p) ?: return
            if (isByte) return
            if (distances[p] <= d) return
            distances[p] = d
            q += p
        }
        enq(GridPoint2D.ZERO, 0)
        while (q.isNotEmpty()) {
            val pos = q.removeFirst()
            val d = distances[pos]
            for (dir in GridPoint2D.orthoDirs) {
                enq(pos + dir, d + 1)
            }
        }
        if (distances[distances.lowerRight] == Int.MAX_VALUE) return p
    }
    error("Nothing found")
}

/**
 * Part2 Fastest
 */
fun day18Part2Raw(input: List<String>): String {
    val a = input.map {
        it.split(',').let { (x, y) -> gridPoint2D(x.toInt(), y.toInt()) }
    }
    val n = 71
    val c = Array(n) { BooleanArray(n) }
    // The neighbor directions
    val di = intArrayOf(0, 1, 0, -1)
    val dj = intArrayOf(1, 0, -1, 0)
    for ((ic, jc) in a) {
        c[ic][jc] = true
        val dist = Array(n) { IntArray(n) { Int.MAX_VALUE } }
        val q = ArrayDeque<GridPoint2D>()
        fun enq(i: Int, j: Int, d: Int) {
            if (i !in 0..<n || j !in 0..<n) return
            if (c[i][j]) return
            if (dist[i][j] <= d) return
            dist[i][j] = d
            q += gridPoint2D(i, j)
        }
        enq(0, 0, 0)
        while (q.isNotEmpty()) {
            val (i, j) = q.removeFirst()
            val d = dist[i][j]
            for (k in 0..3) {
                enq(i + di[k], j + dj[k], d + 1)
            }
        }
        if (dist[n - 1][n - 1] == Int.MAX_VALUE) {
            return "$ic,$jc"
        }
    }
    error("Not found")
}
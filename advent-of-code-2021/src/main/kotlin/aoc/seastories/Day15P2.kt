package aoc.seastories

import aoc.utils.grid2d.*
import aoc.utils.grid2d.GridPoint2D as P2
import java.util.*

/**
 * https://github.com/elizarov/AdventOfCode2021/blob/main/src/Day15_2_fast.kt
 *
 * Refactored to understand better
 */
internal fun day15Part1(input: List<String>): Int {
    val grid = input.toIntGrid { it.digitToInt() }
    return dijkstra(grid)[grid.endExclusive].dist
}
internal fun day15Part2(input: List<String>): Int {
    val grid = enlarge(input, 5)
    val target = dijkstra(grid)[grid.endExclusive]
    val path = target.path().toList()
    path.forEach { println(it.position) }
    return target.dist
}

private fun dijkstra(grid: IntGrid): Grid<Grid2DNode> {
    val a = Array(grid.height) { y ->
        Array(grid.width) { x -> Grid2DNode(gridPoint2D(x, y), Int.MAX_VALUE) }
    }
    val s = Grid2DNode(P2.ZERO, 0)
    a[P2.ZERO] = s
    val v = Array(grid.height) { BooleanArray(grid.width) }
    val q = PriorityQueue(compareBy(Grid2DNode::dist))
    q.add(s)
    while (!v[grid.endExclusive]) {
        val cur = q.remove()
        val p = cur.position
        if (v[p]) continue
        v[p] = true
        for (dir in P2.orthoDirs) {
            val n = p + dir
            grid.getOrNull(n)?.let { dist ->
                val newDistance = cur.dist + dist
                if (newDistance < a[n].dist) {
                    val nn = Grid2DNode(n, newDistance, cur)
                    a[n] = nn
                    q += nn
                }
            }
        }
    }
    return a.toGrid { it }
}

private fun enlarge(input: List<String>, times: Int): IntGrid {
    val a0 = input.toMutableIntGrid { it.digitToInt() }
    val n0 = a0.size
    val m0 = a0[0].size
    val n = times * n0
    val m = times * m0
    return buildIntGrid(m, n) { i, j ->
        val k = i / n0 + j / m0
        (a0[i % n0][j % m0] + k - 1) % 9 + 1
    }
}
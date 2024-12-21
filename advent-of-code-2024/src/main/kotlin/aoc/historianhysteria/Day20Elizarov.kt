package aoc.historianhysteria

import aoc.utils.grid2d.*
import kotlin.math.abs
import aoc.utils.grid2d.GridPoint2D as P2
import aoc.utils.grid2d.gridPoint2D as P2

fun day20Part1(input: List<String>): Int = solve(input, 2)
fun day20Part2(input: List<String>): Int = solve(input, 20)

private const val savedPicoSeconds = 100

private fun solve(input: List<String>, nrCheatPs: Int): Int {
    val d = input.dimension2D()
    val start = input.findPoint { it == 'S' } ?: error("'S' not found")
    val goal = input.findPoint { it == 'E' } ?: error("'E' not found")
    val ds = floodFill(d, start) { input[it.position] != '#' }
    val de = floodFill(d, goal) { input[it.position] != '#' }
    val d0 = ds[goal]
    check(de[start].dist == d0.dist)
    var cnt = 0
    input.forEachPoint {
        for (dx in -nrCheatPs..nrCheatPs) for (dy in -nrCheatPs..nrCheatPs) {
            val r = abs(dx) + abs(dy)
            if (r <= nrCheatPs) {
                val n = it + P2(dx, dy)
                val dn = ds.getOrNull(n) ?: continue
                if (dn.dist + de[it].dist + r <= d0.dist - savedPicoSeconds) {
                    cnt++
                }
            }
        }
    }
    return cnt
}

/**
 * Finds 4-distances in a grid:
 * - Maze dimension is [d].
 * - Start point is [start]) with distance of 0.
 * - Unreachable cells are marked with [inf] distance.
 * - Moves in 4 directions are allowed.
 * - Only cells where [allow] predicate returns true can be moved into.
 * - `allow(p2, d)` is called on cell (p2) that is distance `d` from the start.
 */
fun floodFill(d: Dimension2D, start: P2, inf: Int = Int.MAX_VALUE / 4, allow: (Grid2DNode) -> Boolean): Grid<Grid2DNode> {
    val ds = Array(d.height) { y -> Array(d.width) { x -> Grid2DNode(P2(x, y), inf) } }
    val q = ArrayDeque<Grid2DNode>()
    val sn = start.withDistance(0)
    ds[start] = sn
    q += sn
    while (q.isNotEmpty()) {
        val cur = q.removeFirst()
        val dNew = ds[cur.position].dist + 1
        for (dir in P2.orthoDirs) {
            val n = cur.position + dir
            val cn = ds.getOrNull(n) ?: continue
            val nn = Grid2DNode(n, dNew, cur)
            if (cn.dist == inf && allow(nn)) {
                ds[n] = nn
                q += nn
            }
        }
    }
    return ds.toGrid { it }
}

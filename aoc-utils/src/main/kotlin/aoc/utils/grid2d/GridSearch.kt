package aoc.utils.grid2d

import aoc.utils.grid2d.gridPoint2D
import java.util.*
import kotlin.collections.ArrayDeque
import aoc.utils.grid2d.GridPoint2D as P2
import aoc.utils.grid2d.gridPoint2D as P2

private const val DEFAULT_COST = Int.MAX_VALUE / 4

/**
 * A breadth first search solution to find distances and paths
 *
 * Finds 4-distances in a grid:
 * - Maze dimension is [dimension].
 * - Start point is [start]) with distance of 0.
 * - Unreachable cells are marked with [inf] distance.
 * - Moves in 4 directions are allowed.
 * - Only cells where [isNeighbor] predicate returns true can be moved into.
 * - `allow(node)` is called on cell (p2) that is distance `d` from the start.
 */
fun floodFill(
    dimension: Dimension2D,
    start: P2,
    inf: Int = DEFAULT_COST,
    isNeighbor: (Grid2DNode) -> Boolean
): Map<P2, Grid2DNode> = buildMap {
    val ds = Array(dimension.height) { y ->
        Array(dimension.width) { x -> Grid2DNode(P2(x, y), inf) }
    }
    val q = ArrayDeque<Grid2DNode>()
    val sn = start.withDistance(0)
    ds[start] = sn
    q += sn
    while (q.isNotEmpty()) {
        val cur = q.removeFirst()
        this[cur.position] =cur
        val newCost = ds[cur.position].cost + 1
        for (dir in P2.orthoDirs) {
            val np = cur.position + dir
            val n = ds.getOrNull(np) ?: continue
            val nn = Grid2DNode(position = np, cost = newCost, prev = cur)
            if (n.cost == inf && isNeighbor(nn)) {
                ds[np] = nn
                q += nn
            }
        }
    }
}

fun <T> Grid<T>.dijkstra(start: P2, goal: P2, costAt: (P2) -> Int): Grid2DNode {
    val a = Array(height) { y ->
        Array(width) { x -> Grid2DNode(gridPoint2D(x, y), Int.MAX_VALUE) }
    }
    val s = Grid2DNode(start, 0)
    a[start] = s
    val visited = Array(height) { BooleanArray(width) }
    val q = PriorityQueue(compareBy(Grid2DNode::cost))
    q.add(s)
    while (!visited[goal]) {
        val cur = q.remove()
        val p = cur.position
        if (visited[p]) continue
        visited[p] = true
        for (dir in P2.orthoDirs) {
            val np = p + dir
            getOrNull(np)?.let {
                val newCost = cur.cost + costAt(np)
                if (newCost < a[np].cost) {
                    val n = Grid2DNode(np, newCost, cur)
                    a[np] = n
                    q += n
                }
            }
        }
    }
    return a[goal]
}

package aoc.snowrescuemission

import aoc.utils.Heap
import aoc.utils.grid2d.gridAsString
import aoc.utils.grid2d.GridPoint2D
import aoc.utils.grid2d.toMutableCharGrid

/**
 * https://github.com/elizarov/AdventOfCode2023/blob/main/src/Day17_1.kt
 */
fun day17Part2(input: List<String>): Int {
    val towerDirs = listOf(GridPoint2D.down, GridPoint2D.right, GridPoint2D.up, GridPoint2D.left)

    data class Pos(
        override val x: Int,
        override val y: Int,
        val dir: Int, // 0 north, 1 east, 2 south, 3 west
        val straightLength: Int,
        val prev: GridPoint2D? = null
    ) : GridPoint2D

    val heap = Heap<Pos, Int>()
    val visited = HashSet<Pos>()
    fun enq(x: Int, y: Int, dir: Int, straightLength: Int, heatLoss: Int, prev: GridPoint2D? = null) {
        val position = Pos(x, y, dir, straightLength, prev)
        if (position in visited) return
        heap.putBetter(position, heatLoss)
    }
    fun gridToString(input: List<String>, p: Pos): String {
        val grid = input.toMutableCharGrid()
        var cur: GridPoint2D? = p
        var c = 0
        while (cur != null) {
            println(cur)
            grid[cur.x][cur.y] = 'X'
            if (cur == GridPoint2D.ZERO) break
            cur = p.prev
            if (c == 100) break
            c++
        }
        return grid.gridAsString()
    }

    enq(0, 0, 0, 0, 0)
    val n = input.size
    val m = input[0].length
    while (true) {
        val (p, minHeatLoss) = heap.removeMin()
        visited += p
        if (p.x == n - 1 && p.y == m - 1) {
//            println("grid")
//            println(gridToString(input, p))
            return minHeatLoss
        }
        for (dir in -1..1) {
            when (dir) {
                0 -> if (p.straightLength == 10) continue
                else -> if (p.straightLength < 4) continue
            }
            val nextDir = (p.dir + dir).mod(4)
            val straightLength = if (dir == 0) p.straightLength + 1 else 1
            val neighbor = p + towerDirs[nextDir]
            input.getOrNull(neighbor.x)?.getOrNull(neighbor.y)?.let {
                enq(
                    x = neighbor.x,
                    y = neighbor.y,
                    dir = nextDir,
                    straightLength = straightLength,
                    heatLoss = minHeatLoss + (it - '0'),
                    prev = p
                )
            }
        }
    }
}

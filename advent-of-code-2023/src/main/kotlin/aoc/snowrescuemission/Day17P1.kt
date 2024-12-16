package aoc.snowrescuemission

import aoc.utils.Heap
import aoc.utils.getOrNull
import aoc.utils.model.GridPoint2D

/**
 * https://github.com/elizarov/AdventOfCode2023/blob/main/src/Day17_1.kt
 */
fun day17Part1(input: List<String>): Int {
    val towerDirs = listOf(GridPoint2D.down, GridPoint2D.right, GridPoint2D.up, GridPoint2D.left)
    val maxStraightLength = 3

    data class Pos(
        override val x: Int,
        override val y: Int,
        val dir: Int, // 0 north, 1 east, 2 south, 3 west
        val straightLength: Int
    ) : GridPoint2D

    val heap = Heap<Pos, Int>()
    val visited = HashSet<Pos>()
    fun enq(x: Int, y: Int, dir: Int, straightLength: Int, heatLoss: Int) {
        val position = Pos(x, y, dir, straightLength)
        if (position in visited) return
        heap.putBetter(position, heatLoss)
    }
    enq(0, 0, 0, 0, 0)
    val n = input.size
    val m = input[0].length
    while (true) {
        val (p, minHeatLoss) = heap.removeMin()
        visited += p
        if (p.x == n - 1 && p.y == m - 1) {
            return minHeatLoss
        }
        for (dir in -1..1) {
            if (dir == 0 && p.straightLength == maxStraightLength) continue
            val nextDir = (p.dir + dir).mod(4)
            val straightLength = if (dir == 0) p.straightLength + 1 else 1
            val neighbor = p + towerDirs[nextDir]
            input.getOrNull(neighbor)?.let {
                enq(
                    x = neighbor.x,
                    y = neighbor.y,
                    dir = nextDir,
                    straightLength = straightLength,
                    heatLoss = minHeatLoss + (it - '0')
                )
            }
        }
    }
}

package aoc.seastories

import aoc.utils.grid2d.*
import aoc.utils.grid2d.GridPoint2D as P2

/**
 * https://github.com/elizarov/AdventOfCode2021/blob/main/src/Day15_2_fast.kt
 *
 * Refactored to understand better
 */
internal fun day15Part1(input: List<String>): Int {
    val grid = input.toGrid { it.digitToInt() }
    return grid.dijkstra(start = P2.ZERO, goal = grid.endExclusive) { grid[it] }.cost
}

internal fun day15Part2(input: List<String>): Grid2DNode {
    val grid = enlarge(input, 5)
    return grid.dijkstra(start = P2.ZERO, goal = grid.endExclusive) { grid[it] }
}

private fun enlarge(input: List<String>, times: Int): Grid<Int> {
    val a0 = input.toMutableIntGrid { it.digitToInt() }
    val n0 = a0.size
    val m0 = a0[0].size
    val n = times * n0
    val m = times * m0
    return buildGrid(m, n) { i, j ->
        val k = i / n0 + j / m0
        (a0[i % n0][j % m0] + k - 1) % 9 + 1
    }
}
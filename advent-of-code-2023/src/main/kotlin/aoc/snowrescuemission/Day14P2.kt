package aoc.snowrescuemission

import aoc.utils.grid2d.*
import aoc.utils.grid2d.GridPoint2D.Companion.north
import aoc.utils.invoke

/**
 * [Source](https://github.com/elizarov/AdventOfCode2023/blob/main/src/Day14_2.kt)
 *
 * The trick is to realize that the pattern is repeated so you don't have to actually compute 1_000_000_000 cycles.
 *
 * By maintaining a map of data to count and vice versa, the cycle can be detected.
 */
fun day14part2(input: List<String>): Int {
    class Data(val grid: Array<CharArray>) {
        override fun equals(other: Any?): Boolean = other is Data && grid.contentDeepEquals(other.grid)
        override fun hashCode(): Int = grid.contentDeepHashCode()
        override fun toString(): String = grid.joinToString("\n") { it.joinToString("") }
    }

    var grid = input.toMutableCharGrid()
    val (m, n) = grid.dimension2D()

    val data2count = HashMap<Data, Int>()
    val count2Data = HashMap<Int, Data>()
    var count = 0
    val target = 1_000_000_000
    while (true) {
        val data = Data(grid = Array(grid.size) { grid[it].copyOf() })
        val start = data2count.put(data, count)
        if (start != null) {
            val targetData = count2Data(start + (target - start) % (count - start))
            for (y in 0..<n) for (x in 0..<m) grid[y][x] = targetData.grid[y][x]
            break
        }
        count2Data[count++] = data
        grid = executeCycle(grid)
    }
    return calculateLoad(grid)
}

private fun calculateLoad(grid: Array<CharArray>): Int {
    var sum = 0
    for ((y, r) in grid.withIndex()) {
        for (x in r.indices) {
            if (grid[y][x] == 'O') {
                sum += r.size - y
            }
        }
    }
    return sum
}

private fun executeCycle(grid: Array<CharArray>): Array<CharArray> {
    var grid = grid
    repeat(4) {
        grid.forEachPoint { p ->
            if (grid[p] == 'O') {
                var dir = p
                while (true) {
                    grid.getOrNull(dir + north).takeIf { it == '.' } ?: break
                    dir += north
                }
                grid[p] = '.'
                grid[dir] = 'O'
            }
        }
        grid = grid.rotated()
    }
    return grid
}

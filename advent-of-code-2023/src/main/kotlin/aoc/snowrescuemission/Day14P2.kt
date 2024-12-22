package aoc.snowrescuemission

import aoc.utils.grid2d.toMutableCharGrid
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

    val grid = input.toMutableCharGrid()
    val m = grid.size
    val n = grid[0].size

    val data2count = HashMap<Data, Int>()
    val count2Data = HashMap<Int, Data>()
    var count = 0
    val target = 1_000_000_000
    while (true) {
        val data = Data(grid = Array(grid.size) { grid[it].copyOf() })
        val start = data2count.put(data, count)
        if (start != null) {
            val targetData = count2Data(start + (target - start) % (count - start))
            for (i in 0..<m) for (j in 0..<n) grid[i][j] = targetData.grid[i][j]
            break
        }
        count2Data[count++] = data
        executeCycle(n, m, grid)
    }
    return calculateLoad(m, n, grid)
}

private fun calculateLoad(m: Int, n: Int, grid: Array<CharArray>): Int {
    var sum = 0
    for (i in 0..<m) {
        for (j in 0..<n) {
            if (grid[i][j] == 'O') {
                sum += m - i
            }
        }
    }
    return sum
}

private fun executeCycle(n: Int, m: Int, grid: Array<CharArray>) {
    // north
    for (j in 0..<n) {
        for (i in 0..<m) {
            if (grid[i][j] == 'O') {
                var k = i
                while (k > 0 && grid[k - 1][j] == '.') k--
                grid[i][j] = '.'
                grid[k][j] = 'O'
            }
        }
    }
    // west
    for (i in 0..<m) {
        for (j in 0..<n) {
            if (grid[i][j] == 'O') {
                var k = j
                while (k > 0 && grid[i][k - 1] == '.') k--
                grid[i][j] = '.'
                grid[i][k] = 'O'
            }
        }
    }
    // south
    for (j in 0..<n) {
        for (i in m - 1 downTo 0) {
            if (grid[i][j] == 'O') {
                var k = i
                while (k < m - 1 && grid[k + 1][j] == '.') k++
                grid[i][j] = '.'
                grid[k][j] = 'O'
            }
        }
    }
    // east
    for (i in 0..<m) {
        for (j in n - 1 downTo 0) {
            if (grid[i][j] == 'O') {
                var k = j
                while (k < n - 1 && grid[i][k + 1] == '.') k++
                grid[i][j] = '.'
                grid[i][k] = 'O'
            }
        }
    }
}

package aoc.historianhysteria

import aoc.utils.grid2d.GridPoint2D
import aoc.utils.grid2d.firstPoint

/**
 * [Source](https://github.com/elizarov/AdventOfCode2024/blob/main/src/Day06_2.kt)
 */
fun day06P2(input: List<String>): Int {
    val n = input.size
    val m = input[0].length
    val start = input.firstPoint { it == '^' }
    val v = Array(4) { Array(n) { BooleanArray(m) } }
    var cnt = 0
    for (ib in 0..<n) for (jb in 0..<m) {
        v.forEach { w -> w.forEach { it.fill(false) } }
        var y = start.y
        var x = start.x
        var d0 = 3
        var found = true
        while (!v[d0][y][x]) {
            v[d0][y][x] = true
            val (dx, dy) = GridPoint2D.towerDirs[d0]
            val i1 = y + dy
            val j1 = x + dx
            if (i1 !in 0..<n || j1 !in 0..<m) {
                found = false
                break
            }
            if (input[i1][j1] == '#' || (i1 == ib && j1 == jb)) {
                d0 = (d0 + 1) % 4
                continue
            }
            y = i1
            x = j1
        }
        if (found) cnt++
    }
    return cnt
}
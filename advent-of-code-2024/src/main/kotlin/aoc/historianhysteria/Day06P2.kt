package aoc.historianhysteria

import aoc.utils.grid2d.*
import aoc.utils.grid2d.GridPoint2D.Companion.towerDirs

/**
 * [Source](https://github.com/elizarov/AdventOfCode2024/blob/main/src/Day06_2.kt)
 */
fun day06P2(input: List<String>): Int {
    val dim = input.dimension2D()
    val start = input.firstPoint { it == '^' }
    val v = Array(towerDirs.size) { Array(dim.height) { BooleanArray(dim.width) } }
    var cnt = 0
    input.forEachPoint { pt ->
        v.forEach { w -> w.forEach { it.fill(false) } }
        var p = start
        var d = 3
        var found = true
        while (!v[d][p]) {
            v[d][p] = true
            val n = p + towerDirs[d]
            if (n !in dim) {
                found = false
                break
            }
            if (input[n] == '#' || n == pt) {
                d = (d + 1) % towerDirs.size
                continue
            }
            p = n
        }
        if (found) cnt++
    }
    return cnt
}
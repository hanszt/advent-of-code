package aoc.historianhysteria

import aoc.utils.grid2d.dimension2D
import aoc.utils.grid2d.floodFill
import aoc.utils.grid2d.forEachPoint
import aoc.utils.grid2d.get

/**
 * Source: https://github.com/elizarov/AdventOfCode2024/blob/main/src/Day10_1.kt
 *
 * Only refactored to understand it better
 */
fun day10Part1Elizarov(map: List<String>): Long {
    var sum = 0L
    map.forEachPoint {
        if (map[it] == '0') {
            floodFill(map.dimension2D(), start = it) { it ->
                val ok = map[it.position] == '0' + it.cost
                if (ok && it.cost == 9) sum++
                ok
            }
        }
    }
    return sum
}

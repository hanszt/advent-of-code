package aoc.historianhysteria

import aoc.utils.forEachPoint
import aoc.utils.get
import aoc.utils.getOrNull
import aoc.utils.model.GridPoint2D

/**
 * Source: https://github.com/elizarov/AdventOfCode2024/blob/main/src/Day10_1.kt
 *
 * Only refactored to understand it better
 */
fun part1Elizarov(map: List<String>): Long {
    val queue = ArrayDeque<GridPoint2D>()
    val visited = HashSet<GridPoint2D>()
    var sum = 0L
    map.forEachPoint {
        if (map[it] == '0') {
            // Bfs
            queue.apply { clear(); add(it) }
            visited.apply { clear(); add(it) }
            while (queue.isNotEmpty()) {
                val p = queue.removeFirst()
                val height = map[p]
                if (height == '9') {
                    sum++
                    continue
                }
                for (dir in GridPoint2D.towerDirs) {
                    val neighbor = p + dir
                    // check if in grid and current height
                    map.getOrNull(neighbor)?.takeIf { it == height + 1 } ?: continue
                    if (visited.add(neighbor)) {
                        queue += neighbor
                    }
                }
            }
        }
    }
    return sum
}

package aoc.historianhysteria

import aoc.utils.model.GridPoint2D
import aoc.utils.model.gridPoint2D

/**
 * Source: https://github.com/elizarov/AdventOfCode2024/blob/main/src/Day10_1.kt
 *
 * Only refactored to understand it better
 */
fun part1Elizarov(map: List<String>): Long {
    val queue = ArrayDeque<GridPoint2D>()
    val visited = HashSet<GridPoint2D>()
    var sum = 0L
    for ((y, r) in map.withIndex()) {
        for (x in r.indices) {
            if (map[y][x] == '0') {
                // Bfs
                val start = gridPoint2D(x, y)
                queue.apply { clear(); add(start) }
                visited.apply { clear(); add(start) }
                while (queue.isNotEmpty()) {
                    val p = queue.removeFirst()
                    val height = map[p.y][p.x]
                    if (height == '9') {
                        sum++
                        continue
                    }
                    for (dir in GridPoint2D.orthoDirs) {
                        val neighbor = p + dir
                        // check if in grid and current height
                        map.getOrNull(neighbor.y)?.getOrNull(neighbor.x)?.takeIf { it == height + 1 } ?: continue
                        if (visited.add(neighbor)) {
                            queue += neighbor
                        }
                    }
                }
            }
        }
    }
    return sum
}

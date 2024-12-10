package aoc.historianhysteria

import aoc.utils.model.GridPoint2D
import aoc.utils.model.gridPoint2D

/**
 * Source: https://github.com/elizarov/AdventOfCode2024/blob/main/src/Day10_2.kt
 *
 * Only refactored to understand it better
 */
fun part2Elizarov(map: List<String>): Long {
    val queue = ArrayDeque<GridPoint2D>()
    val visited = HashMap<GridPoint2D, Long>()
    var sum = 0L
    for ((y, r) in map.withIndex()) {
        for (x in r.indices) {
            if (map[y][x] == '0') {
                // Bfs adapted
                val start = gridPoint2D(x, y)
                queue.apply { clear(); add(start) }
                visited.apply { clear(); put(start, 1) }
                while (queue.isNotEmpty()) {
                    val p = queue.removeFirst()
                    val height = map[p.y][p.x]
                    val rating = visited[p]!!
                    if (height == '9') {
                        sum += rating
                        continue
                    }
                    for (dir in GridPoint2D.orthoDirs) {
                        val neighbor = p + dir
                        // check if in grid and current height
                        map.getOrNull(neighbor.y)?.getOrNull(neighbor.x)?.takeIf { it == height + 1 } ?: continue
                        if (neighbor !in visited) {
                            queue += neighbor
                        }
                        visited[neighbor] = visited.getOrDefault(neighbor, 0) + rating
                    }
                }
            }
        }
    }
    return sum
}

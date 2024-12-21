package aoc.historianhysteria

import aoc.utils.grid2d.forEachPoint
import aoc.utils.grid2d.get
import aoc.utils.grid2d.getOrNull
import aoc.utils.grid2d.GridPoint2D

/**
 * [Source](https://github.com/elizarov/AdventOfCode2024/blob/main/src/Day10_2.kt)
 *
 * Only refactored to understand it better
 */
fun day10Part2(map: List<String>): Long {
    val queue = ArrayDeque<GridPoint2D>()
    val visited = HashMap<GridPoint2D, Long>()
    var sum = 0L
    map.forEachPoint {
        if (map[it] == '0') {
            // Bfs adapted
            val start = it
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
                for (dir in GridPoint2D.towerDirs) {
                    val neighbor = p + dir
                    // check if in grid and current height
                    map.getOrNull(neighbor)?.takeIf { it == height + 1 } ?: continue
                    if (neighbor !in visited) {
                        queue += neighbor
                    }
                    visited[neighbor] = visited.getOrDefault(neighbor, 0) + rating
                }
            }
        }
    }
    return sum
}

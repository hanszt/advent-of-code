package aoc.historianhysteria

import aoc.utils.ChallengeDay
import aoc.utils.model.GridPoint2D
import aoc.utils.model.gridPoint2D
import java.nio.file.Path
import java.util.*
import kotlin.io.path.readLines

class Day10(private val map: List<String>) : ChallengeDay {
    constructor(path: Path) : this(path.readLines())

    override fun part1(): Long {
        var result = 0L
        for ((y, r) in map.withIndex()) {
            for (x in r.indices) {
                val height = toHeight(x, y) ?: continue
                if (height == 0) {

                    val start = gridPoint2D(x, y)
                    /**
                     * You establish that a trailhead's score is the number of 9-height positions reachable from that trailhead via a hiking trail.
                     */
                    val count = allPathsByDfs(start)
                        .distinctBy { it.last() }
                        .count()
                    result += count
                }
            }
        }
        return result
    }

    /**
     * See https://www.geeksforgeeks.org/find-paths-given-source-destination/
     */
    fun allPathsByDfs(start: GridPoint2D): Sequence<List<GridPoint2D>> = sequence {
        dfsRecursive(current = start)
    }

    private suspend fun SequenceScope<List<GridPoint2D>>.dfsRecursive(
        current: GridPoint2D,
        visited: MutableSet<GridPoint2D> = HashSet(),
        localPath: MutableList<GridPoint2D> = mutableListOf(current),
    ) {
        visited += current
        val curHeight = toHeight(current) ?: error("value at $current not found")
        if (curHeight == 9) {
            yield(localPath.toList())
        }
        for (dir in GridPoint2D.towerDirs) {
            val neighbor = current + dir
            val neighborHeight = toHeight(neighbor)
            neighborHeight?.takeIf { it == curHeight + 1 } ?: continue
            if (neighbor !in visited) {
                localPath += neighbor
                dfsRecursive(neighbor, visited, localPath)
                localPath -= neighbor
            }
        }
    }

    private fun toHeight(gridPoint2D: GridPoint2D): Int? = toHeight(x = gridPoint2D.x, y = gridPoint2D.y)
    private fun toHeight(x: Int, y: Int): Int? = map.getOrNull(y)?.getOrNull(x)?.takeIf { it.isDigit() }?.digitToInt()

    fun part1Elizarov(): Long = part1Elizarov(map)
    override fun part2(): Long = part2Elizarov(map)
}

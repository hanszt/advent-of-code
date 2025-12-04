package aoc.secret_entrance

import aoc.utils.ChallengeDay
import aoc.utils.grid2d.*
import java.nio.file.Path
import kotlin.io.path.readLines

class Day04(private val lines: List<String>) : ChallengeDay {
    constructor(path: Path) : this(path.readLines())

    override fun part1(): Int = lines.gridCount { it.isAccessible(lines::getOrNull) }

    override fun part2(): Int {
        var grid = lines.toMutableCharGrid()
        var totalRemoved = 0
        do {
            var removed = 0
            val next = grid.copyGrid()
            grid.forEachPoint {
                val shouldBeRemoved = it.isAccessible(grid::getOrNull)
                next[it] = if (shouldBeRemoved) 'X' else grid[it]
                if (shouldBeRemoved) removed++
            }
            grid = next
            totalRemoved += removed
        } while (removed > 0)
        return totalRemoved
    }

    private fun GridPoint2D.isAccessible(toCharOrNull: GridPoint2D.() -> Char?): Boolean =
        toCharOrNull(this) == '@' && (GridPoint2D.kingDirs.count { d -> toCharOrNull(this + d) == '@' } < 4)
}

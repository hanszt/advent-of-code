package aoc.historianhysteria

import aoc.utils.*
import aoc.utils.grid2d.GridPoint2D
import aoc.utils.grid2d.Vec2
import aoc.utils.grid2d.findPoint
import aoc.utils.grid2d.forEachPoint
import aoc.utils.grid2d.getOrNull
import aoc.utils.grid2d.gridAsString
import aoc.utils.grid2d.set
import aoc.utils.grid2d.toMutableCharGrid
import java.nio.file.Path
import kotlin.io.path.readText

class Day06(input: String) : ChallengeDay {

    private val map = input.lines()
    private val guardStart = '^'
    private val guardStartPos = map.findPoint { it == guardStart } ?: error("$guardStart not found")

    constructor(path: Path) : this(path.readText())

    private val orientationMap = mapOf(
        GridPoint2D(0, -1) to '^',
        GridPoint2D(1, 0) to '>',
        GridPoint2D(0, 1) to 'v',
        GridPoint2D(-1, 0) to '<',
    )

    /**
     * How many distinct positions will the guard visit before leaving the mapped area?
     */
    override fun part1(): Int = walkedPath(guardStartPos).distinct().count()

    private fun walkedPath(start: GridPoint2D): Sequence<GridPoint2D> = sequence {
        val obstacle = '#'
        var cur = start
        var curDir = GridPoint2D(0, -1)
        while (map.getOrNull(cur) != null) {
            yield(cur)
            val inFront = cur + curDir
            if (map.getOrNull(inFront) == obstacle) {
                curDir = curDir.rot90R()
            }
            cur = cur + curDir
        }
    }

    /**
     * How many different positions could you choose for this obstruction?
     */
    override fun part2(): Int {
        return day06P2(map)
        var result = 0
        map.forEachPoint { x, y ->
            val ch = map[y][x]
            if (ch == '.') {
                if (isCycle(extraObstaclePos = GridPoint2D(x, y))) {
                    result++
                }
            }
        }
        return result
    }

    private fun isCycle(extraObstaclePos: GridPoint2D): Boolean {
        val obstacle = '#'
        var curPos = guardStartPos
        var curDir = GridPoint2D(0, -1)
        val set = HashSet<Heading>()
        while (map.getOrNull(curPos) != null) {
            if (!set.add(Heading(position = curPos, dir = curDir))) {
                return true
            }
            val inFront = curPos + curDir
            if (map.getOrNull(inFront) == obstacle || inFront == extraObstaclePos) {
                curDir = curDir.rot90R()
            }
            curPos += curDir
        }
        return false
    }

    private fun loopToString(headings: Set<Heading>, extraObstaclePos: GridPoint2D): String {
        val grid = map.toMutableCharGrid()
        for (heading in headings) {
            val position = heading.position
            grid[position] = orientationMap[heading.dir]!!
        }
        grid[extraObstaclePos.y][extraObstaclePos.x] = 'O'
        return grid.gridAsString()
    }

    data class Heading(val position: GridPoint2D, val dir: Vec2)
}
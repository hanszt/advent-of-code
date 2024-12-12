package aoc.historianhysteria

import aoc.utils.ChallengeDay
import aoc.utils.findPoint
import aoc.utils.forEachPoint
import aoc.utils.model.GridPoint2D
import aoc.utils.model.gridPoint2D
import java.nio.file.Path
import kotlin.io.path.readText

class Day06(input: String) : ChallengeDay {

    private val map = input.lines()
    private val guardStart = '^'
    private val guardStartPos = map.findPoint { it == guardStart } ?: error("$guardStart not found")

    constructor(path: Path) : this(path.readText())

    private val orientationMap = mapOf(
         gridPoint2D(0, -1) to '^',
         gridPoint2D(1, 0) to '>',
         gridPoint2D(0, 1) to 'v',
         gridPoint2D(-1, 0) to '<',
    )

    override fun part1(): Int {
        return walkedPath(guardStartPos).distinct().count()
    }

    private fun walkedPath(start: GridPoint2D): Sequence<GridPoint2D> = sequence {
        val obstacle = '#'
        var cur = start
        var curDir = gridPoint2D(0, -1)
        while (map.getOrNull(cur.y)?.getOrNull(cur.x) != null) {
            yield(cur)
            val inFront = cur + curDir
            if (map.getOrNull(inFront.y)?.getOrNull(inFront.x) == obstacle) {
                curDir = curDir.rot90R()
            }
            cur = cur + curDir
        }
    }

    override fun part2(): Int {
        return day06P2(map)
        var result = 0
        map.forEachPoint { x, y ->
            val ch = map[y][x]
            if (ch == '.') {
                if (isCycle(extraObstaclePos = gridPoint2D(x, y))) {
                    result++
                }
            }
        }
        return result
    }

    private fun isCycle(extraObstaclePos: GridPoint2D): Boolean {
        val obstacle = '#'
        var curPos = guardStartPos
        var curDir = gridPoint2D(0, -1)
        val set = HashSet<Heading>()
        while (map.getOrNull(curPos.y)?.getOrNull(curPos.x) != null) {
            if (!set.add(Heading(position = curPos, dir = curDir))) {
                return true
            }
            val inFront = curPos + curDir
            if (map.getOrNull(inFront.y)?.getOrNull(inFront.x) == obstacle || inFront == extraObstaclePos) {
                curDir = curDir.rot90R()
            }
            curPos += curDir
        }
        return false
    }

//    private fun loopToString(headings: Set<Heading>, extraObstaclePos: GridPoint2D): String {
//        val grid = map.toGridOf { it }
//        for (heading in headings) {
//            val position = heading.position
//            val dir = heading.dir
//            grid[position.y][position.x] = orientationMap[dir]!!
//            grid[extraObstaclePos.y][extraObstaclePos.x] = 'O'
//        }
//        return grid.gridAsString()
//    }

    data class Heading(val position: GridPoint2D, val dir: GridPoint2D)
}
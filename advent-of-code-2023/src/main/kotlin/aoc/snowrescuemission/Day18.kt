package aoc.snowrescuemission

import aoc.utils.ChallengeDay
import aoc.utils.grid2d.*
import aoc.utils.invoke
import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.math.abs

class Day18(private val digPlan: List<String>) : ChallengeDay {
    constructor(fileName: String) : this(Path(fileName).readLines())

    private companion object {
        private val dirs = listOf('R', 'D', 'L', 'U').zip(GridPoint2D.towerDirs).toMap()
    }

    /**
     * How many cubic meters of lava could it hold?
     */
    override fun part1(): Int {
        return p1Elizarov()
        val map = HashMap<GridPoint2D, String>()
        var cur = GridPoint2D.ZERO
        for (tr in digPlan) {
            var (dir, amount, color) = tr.split(' ')
            var d = dirs(dir[0])
            repeat(amount.toInt()) {
                var n = cur + d
                map[n] += color
                cur = n
            }
        }
        val grid = map.keys.toMutableGrid { x, y, isWall -> if (isWall) '#' else '.' }
        println(grid.gridAsString())
        println()
        var result = grid.toMutableGrid { it }
        var isInside = false
        grid.forEachPoint { p ->
            val cur = grid[p]
            println(p)
            println(isInside)
            val point = p.plusX(1)
            val next = grid.getOrNull(point)
            if (cur != next) {
                isInside = !isInside
            }
            if (next == null) isInside = false
            if (isInside) result[point] = '#'
        }
        println(result.gridAsString())
        return result.flatten().count { it == '#' }
    }

    fun p1Elizarov(): Int {
        val dn = "RDLU"
        var c = GridPoint2D.ZERO
        var sa = 0
        var sk = 0
        for (ss in digPlan) {
            val (dir, amount) = ss.split(" ")
            val n = c + GridPoint2D.towerDirs[dn.indexOf(dir[0])] * amount.toInt()
            sa += (n.y - c.y) * (n.x + c.x)
            sk += amount.toInt()
            c = n
        }
        return abs(sa) / 2 + abs(sk) / 2 + 1
    }

    override fun part2(): Int {
        TODO()
    }
}

package aoc.snowrescuemission

import aoc.utils.ChallengeDay
import aoc.utils.grid2d.GridPoint2D.Companion.ZERO
import aoc.utils.grid2d.GridPoint2D.Companion.orthoDirs
import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.math.abs

class Day18(private val digPlan: List<String>) : ChallengeDay {
    constructor(fileName: String) : this(Path(fileName).readLines())

    /**
     * Solution Elizarov
     *
     * How many cubic meters of lava could it hold?
     */
    override fun part1(): Int {
        val dirs = "RDLU"
        var c = ZERO
        var sa = 0
        var sk = 0
        for (ss in digPlan) {
            val (dir, amount) = ss.split(" ")
            val n = c + orthoDirs[dirs.indexOf(dir[0])] * amount.toInt()
            sa += (n.y - c.y) * (n.x + c.x)
            sk += amount.toInt()
            c = n
        }
        return abs(sa) / 2 + abs(sk) / 2 + 1
    }

    /**
     * Solution Elizarov
     *
     * How many cubic meters of lava could the lagoon hold?
     */
    override fun part2(): Long {
        var c = ZERO
        var sa = 0L
        var sk = 0L
        for (ss in digPlan) {
            val (_, _, color) = ss.split(" ")
            val hex = color.removeSurrounding("(#", ")")
            val dist = hex.dropLast(1).toInt(radix = 16)
            val d = hex.last() - '0'
            val n = c + orthoDirs[d] * dist
            sa += (n.y - c.y).toLong() * (n.x + c.x)
            sk += dist
            c = n
        }
        return abs(sa) / 2 + sk / 2 + 1
    }
}

package aoc.secret_entrance

import aoc.utils.ChallengeDay
import aoc.utils.grid2d.GridPoint2D
import aoc.utils.grid2d.dimension2D
import java.nio.file.Path
import kotlin.io.path.readLines
import kotlin.math.abs

/**
 *
 */
class Day09(private val lines: List<String>) : ChallengeDay {

    constructor(path: Path) : this(path.readLines())

    override fun part1(): Long {
        var largestArea = 0L
        for (i in 0..<lines.size) {
            for (j in i + 1..<lines.size) {
                val point = toPoint(i)
                val other = toPoint(j)
                val d = dimension2D(abs(point.x - other.x) + 1, abs(point.y - other.y) + 1)
                largestArea = maxOf(d.surfaceArea, largestArea)
            }
        }
        return largestArea
    }

    private fun toPoint(i: Int): GridPoint2D = lines[i].split(',').map(String::toInt)
        .let { (x, y) -> GridPoint2D(x, y) }


    override fun part2(): Int = TODO()
}

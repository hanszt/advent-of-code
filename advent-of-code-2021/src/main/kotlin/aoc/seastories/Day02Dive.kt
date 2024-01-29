package aoc.seastories

import aoc.utils.mapSecond
import aoc.utils.model.GridPoint2D
import aoc.utils.model.GridPoint2D.Companion.by
import aoc.utils.toEnds
import java.io.File

internal class Day02Dive(private val filePath: String) : ChallengeDay {

    override fun part1() = File(filePath).useLines { lines ->
        lines.toDirAndStepSizes()
            .map { (dir, size) -> toVector(dir, size) }
            .reduce(GridPoint2D::plus)
            .run { x * y }
    }

    private fun toVector(dir: String, size: Int) = when (dir) {
        "up" -> 0 by -size
        "down" -> 0 by size
        "forward" -> size by 0
        else -> 0 by 0
    }

    override fun part2(): Int = File(filePath).useLines {
        val pairs = it.toDirAndStepSizes()
        var result = 0 by 0
        var aim = 0
        for ((dir, stepSize) in pairs) {
            when (dir) {
                "forward" -> result += stepSize by stepSize * aim
                "up" -> aim -= stepSize
                "down" -> aim += stepSize
            }
        }
        return result.x * result.y
    }

    private fun Sequence<String>.toDirAndStepSizes() = map { it.split(" ").toEnds().mapSecond(String::toInt) }
}

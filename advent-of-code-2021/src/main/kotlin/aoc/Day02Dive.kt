package aoc

import model.GridPoint2D
import utils.mapSecond
import utils.toEnds
import java.io.File

internal object Day02Dive : ChallengeDay {

    fun part1(filePath: String) = File(filePath).useLines { lines ->
        lines.toDirAndStepSizes()
            .map { (dir, size) -> toVector(dir, size) }
            .reduce(GridPoint2D::plus)
            .run { x * y }
    }

    private fun toVector(dir: String, size: Int) = when (dir) {
        "up" -> GridPoint2D(0, -size)
        "down" -> GridPoint2D(0, size)
        "forward" -> GridPoint2D(size, 0)
        else -> GridPoint2D(0, 0)
    }

    fun part2(filePath: String): Int {
        val pairs = File(filePath).useLines { it.toDirAndStepSizes() }
        var result = GridPoint2D(0, 0)
        var aim = 0
        for ((dir, stepSize) in pairs) {
            when (dir) {
                "forward" -> result += GridPoint2D(stepSize, stepSize * aim)
                "up" -> aim -= stepSize
                "down" -> aim += stepSize
            }
        }
        return result.x * result.y
    }

    private fun Sequence<String>.toDirAndStepSizes() = map { it.split(" ").toEnds().mapSecond(String::toInt) }.toList()

    override fun part1() = part1(ChallengeDay.inputDir + "/day2.txt")
    override fun part2() = part2(ChallengeDay.inputDir + "/day2.txt")
}

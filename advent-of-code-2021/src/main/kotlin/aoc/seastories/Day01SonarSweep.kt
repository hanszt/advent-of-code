package aoc.seastories

import java.io.File

internal class Day01SonarSweep(private val filePath: String) : ChallengeDay {

    override fun part1() = File(filePath).readLines().map(String::toInt).toDepthIncreaseCount()

    override fun part2() = File(filePath).readLines().map(String::toInt).toWindowIncreaseCount()
    private fun List<Int>.toDepthIncreaseCount(): Int =
        (0 until lastIndex).count { this[it] < this[it + 1] }


    private fun List<Int>.toWindowIncreaseCount(): Int = (0 until size - 3).count {
        val window1 = this[it] + this[it + 1] + this[it + 2]
        val window2 = this[it + 1] + this[it + 2] + this[it + 3]
        window1 < window2
    }

    fun part2V2(filePath: String) = File(filePath).useLines { lines ->
        lines.map(String::toInt)
            .windowed(3)
            .map(List<Int>::sum)
            .zipWithNext()
            .count { (sum1, sum2) -> sum1 < sum2 }
    }

}

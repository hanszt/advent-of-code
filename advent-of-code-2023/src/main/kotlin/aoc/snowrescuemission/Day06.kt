package aoc.snowrescuemission

import aoc.utils.ChallengeDay
import java.io.File

class Day06(
    fileName: String? = null,
    private val lines: List<String> = fileName?.let { File(it).readLines() } ?: error("No lines or fileName provided")
) : ChallengeDay {

    override fun part1(): Long {
        val (times, distances) = lines
            .map {
                it.splitToSequence(' ')
                    .filter(String::isNotEmpty)
                    .drop(1)
                    .map(String::toLong)
            }
        return (times zip distances).fold(1L) { product, (time, distanceToBeat) ->
            product * nrOfWaysToWin(time, distanceToBeat)
        }
    }

    override fun part2(): Long {
        val (time, distanceToBeat) = lines.map { it.split(':').last().trim().replace(" ", "").toLong() }
        return nrOfWaysToWin(time, distanceToBeat)
    }

    private fun nrOfWaysToWin(time: Long, distanceToBeat: Long): Long {
        val minHoldTimeForWin = (0..time).first { it * (time - it) > distanceToBeat }
        val maxHoldTimeForWin = (time downTo 0).first { it * (time - it) > distanceToBeat }
        return maxHoldTimeForWin - minHoldTimeForWin + 1
    }
}

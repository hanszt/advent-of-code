package aoc.snowrescuemission

import aoc.utils.ChallengeDay
import java.io.File

class Day06(
    fileName: String? = null,
    private val lines: List<String> = fileName?.let { File(it).readLines() } ?: error("No text or fileName provided")
) : ChallengeDay {

    override fun part1(): Long {
        val (times, distances) = lines.map { it.split(' ').filter(String::isNotEmpty).drop(1).map(String::toLong) }
        var product = 1L
        for ((time, distanceToBeat) in times.zip(distances)) {
            val minHoldTimeForWin = (0..time).first { it * (time - it) > distanceToBeat }
            val maxHoldTimeForWin = (time downTo 0).first { it * (time - it) > distanceToBeat }
            product *= (maxHoldTimeForWin - minHoldTimeForWin + 1)
        }
        return product
    }

    override fun part2(): Long {
        val (time, distanceToBeat) = lines.map { it.split(':').last().trim().replace(" ", "").toLong() }
        println("time = ${time}")
        println("distanceToBeat = ${distanceToBeat}")
        val minHoldTimeForWin = (0L..time).first { it * (time - it) > distanceToBeat }
        val maxHoldTimeForWin = (time downTo 0L).first { it * (time - it) > distanceToBeat }
        return maxHoldTimeForWin - minHoldTimeForWin + 1L
    }
}

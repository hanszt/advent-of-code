package aoc.snowrescuemission

import aoc.utils.ChallengeDay
import java.io.File
import kotlin.math.abs

class Day01(
    fileName: String? = null,
    private val lines: List<String> = fileName?.let { File(it).readLines() } ?: error("No lines or fileName provided")
) : ChallengeDay {

    override fun part1(): Int {
        val (l1, l2) = lines.asSequence()
            .map { line -> line.split("   ").let { it[0].toInt() to it[1].toInt() } }
            .unzip()
        val l1Sorted = l1.sorted()
        val l2Sorted = l2.sorted()

        return l1Sorted.zip(l2Sorted).sumOf { (l1, l2) -> abs(l1 - l2) }
    }

    override fun part2(): Int {
        val (l1, l2) = lines.asSequence()
            .map { line -> line.split("   ").let { it[0].toInt() to it[1].toInt() } }
            .unzip()
        val eachCount = l2.groupingBy { it }.eachCount()
        return l1.sumOf { it * (eachCount[it] ?: 0) }

    }
}

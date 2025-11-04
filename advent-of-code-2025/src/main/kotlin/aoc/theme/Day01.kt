package aoc.theme

import aoc.utils.ChallengeDay
import java.nio.file.Path
import kotlin.io.path.readLines
import kotlin.math.abs

class Day01(private val lines: List<String>) : ChallengeDay {
    constructor(path: Path) : this(path.readLines())

    override fun part1(): Int {
        val (l1, l2) = lines.asSequence()
            .map { line -> line.split("   ").let { it[0].toInt() to it[1].toInt() } }
            .unzip()
        val l1Sorted = l1.sorted()
        val l2Sorted = l2.sorted()

        return l1Sorted.zip(l2Sorted).sumOf { (n1, n2) -> abs(n1 - n2) }
    }

    override fun part2(): Int {
        val (l1, l2) = lines.asSequence()
            .map { line -> line.split("   ").let { it[0].toInt() to it[1].toInt() } }
            .unzip()
        val eachCount = l2.groupingBy { it }.eachCount()
        return l1.sumOf { it * (eachCount[it] ?: 0) }

    }
}

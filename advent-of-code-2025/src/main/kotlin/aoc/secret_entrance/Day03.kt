package aoc.secret_entrance

import aoc.utils.ChallengeDay
import java.nio.file.Path
import kotlin.io.path.readLines

class Day03(private val lines: List<String>) : ChallengeDay {
    constructor(path: Path) : this(path.readLines())

    override fun part1(): Int = lines.sumOf { findJoltageP1(it) }

    private fun findJoltageP1(string: String): Int {
        var largest = 0
        var largestIndex = 0
        for (i in 0 until string.length) {
            val nr = string[i].digitToInt()
            if (nr > largest) {
                largest = nr
                largestIndex = i
            }
        }
        var secondLargest = 0
        return if (largestIndex == string.lastIndex) {
            for (i in 0 until string.lastIndex) {
                val nr = string[i].digitToInt()
                if (nr > secondLargest) {
                    secondLargest = nr
                }
            }
            "$secondLargest$largest".toInt()
        } else {
            for (i in string.lastIndex downTo largestIndex + 1) {
                val nr = string[i].digitToInt()
                if (nr > secondLargest) {
                    secondLargest = nr
                }
            }
            "$largest$secondLargest".toInt()
        }
    }

    override fun part2(): Long = solve(12)

    /**
     * https://github.com/elizarov/AdventOfCode2025/blob/main/src/Day03_2.kt credits to Elizarov
     */
    internal fun solve(k: Int): Long = lines.sumOf { s ->
        var startIndex = 0
        buildString {
            for (j in 1..k) {
                val (i, a) = s.substring(startIndex).dropLast(k - j).withIndex().maxBy { it.value }
                append(a)
                startIndex += (i + 1)
            }
        }.toLong()
    }
}

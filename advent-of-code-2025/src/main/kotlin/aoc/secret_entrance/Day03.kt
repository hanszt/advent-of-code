package aoc.secret_entrance

import aoc.utils.ChallengeDay
import java.nio.file.Path
import kotlin.io.path.readLines

class Day03(private val lines: List<String>) : ChallengeDay {
    constructor(path: Path) : this(path.readLines())

    override fun part1(): Long = solve(2)
    override fun part2(): Long = solve(12)

    /**
     * https://github.com/elizarov/AdventOfCode2025/blob/main/src/Day03_2.kt credits to Elizarov
     */
    internal fun solve(k: Int): Long = lines.sumOf { s ->
        buildString {
            var prev = 0
            for (j in 1..k) {
                val (i, a) = s.drop(prev).dropLast(k - j).withIndex().maxBy { it.value }
                append(a)
                prev += (i + 1)
            }
        }.toLong()
    }
}

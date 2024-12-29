package aoc.historianhysteria

import aoc.utils.ChallengeDay
import aoc.utils.splitByBlankLine
import java.nio.file.Path
import kotlin.io.path.readText

class Day19(input: String) : ChallengeDay {

    constructor(path: Path) : this(path.readText())

    private val towels: List<String>
    private val designs: List<String>

    init {
        val (patterns, designs) = input.splitByBlankLine()
        this.towels = patterns.split(", ")
        this.designs = designs.lines()
    }

    /**
     * How many designs are possible?
     *
     * From solution Elizarov
     */
    override fun part1(): Int = designs.count { possibleCount(it) > 0L }
    override fun part2(): Long = designs.sumOf(::possibleCount)

    private fun possibleCount(s: String): Long {
        val n = s.length
        val dp = LongArray(n + 1)
        dp[n] = 1
        for (i in n - 1 downTo 0) {
            dp[i] = towels.sumOf { if (s.startsWith(prefix = it, startIndex = i)) dp[i + it.length] else 0 }
        }
        return dp[0]
    }
}

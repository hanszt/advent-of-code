package aoc.secret_entrance

import aoc.utils.ChallengeDay
import java.nio.file.Path
import kotlin.io.path.readLines

/**
 *
 */
class Day07(private val lines: List<String>) : ChallengeDay {
    constructor(path: Path) : this(path.readLines())

    /**
     * [Elizarov's solution](https://github.com/elizarov/AdventOfCode2025/blob/main/src/Day07_1.kt)
     */
    override fun part1(): Int {
        var xPositions = setOf(lines[0].indexOf('S'))
        var cnt = 0
        for (y in 1..<lines.size) {
            val next = HashSet<Int>()
            for (x in xPositions) {
                if (lines[y][x] == '^') {
                    cnt++
                    next += (x - 1)
                    next += (x + 1)
                } else {
                    next += x
                }
            }
            xPositions = next
        }
        return cnt
    }


    /**
     * [aoc.utils.Tag.MANY_WORLDS]
     *
     * [Elizarov's solution](https://github.com/elizarov/AdventOfCode2025/blob/main/src/Day07_2.kt)
     */
    override fun part2(): Long {
        var xPositionsToCount = mapOf(lines[0].indexOf('S') to 1L)
        for (y in 1..<lines.size) {
            val next = HashMap<Int, Long>()
            for ((x, c) in xPositionsToCount) {
                if (lines[y][x] == '^') {
                    next[x - 1] = next.getOrDefault(x - 1, 0) + c
                    next[x + 1] = next.getOrDefault(x + 1, 0) + c
                } else {
                    next[x] = next.getOrDefault(x, 0) + c
                }
            }
            xPositionsToCount = next
        }
        return xPositionsToCount.values.sum()
    }
}

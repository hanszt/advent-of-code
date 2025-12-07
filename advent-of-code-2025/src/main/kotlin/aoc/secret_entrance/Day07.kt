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
        var rayXPositions = setOf(lines[0].indexOf('S'))
        var cnt = 0
        for (y in 1..<lines.size) {
                rayXPositions = buildSet {
                    for (x in rayXPositions) {
                        if (lines[y][x] == '^') {
                            cnt++
                            this += (x - 1)
                            this += (x + 1)
                        } else {
                            this += x
                        }
                    }
                }
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
            xPositionsToCount = buildMap {
                for ((x, c) in xPositionsToCount) {
                    if (lines[y][x] == '^') {
                        this[x - 1] = getOrDefault(x - 1, 0) + c
                        this[x + 1] = getOrDefault(x + 1, 0) + c
                    } else {
                        this[x] = getOrDefault(x, 0) + c
                    }
                }
            }
        }
        return xPositionsToCount.values.sum()
    }
}

package aoc.secret_entrance

import aoc.utils.ChallengeDay
import java.nio.file.Path
import kotlin.io.path.readLines

class Day01(private val lines: List<String>) : ChallengeDay {
    constructor(path: Path) : this(path.readLines())

    override fun part1(): Int {
        var timesAtZero = 0
        var dial = 50
        for (line in lines) {
            val amount = line.drop(1).toInt()
            dial = when (line.first()) {
                'R' -> dial + amount
                'L' -> dial - amount
                else -> error("Invalid direction: $line")
            } % 100
            if (dial == 0) timesAtZero++
        }
        return timesAtZero
    }

    /**
     * Calculates the number of times a position wraps https://github.com/elizarov/AdventOfCode2025/blob/main/src/Day01.kt
     */
    override fun part2(): Int {
        var cnt = 0
        var pos = 50
        for (line in lines) {
            val amount = line.substring(1).toInt()
            when (line[0]) {
                'R' -> {
                    pos += amount
                    cnt += pos / 100
                }

                'L' -> {
                    if (pos == 0) cnt--
                    pos -= amount
                    cnt += (-pos + 100) / 100
                }
            }
            pos = pos.mod(100)
        }
        return cnt
    }
}

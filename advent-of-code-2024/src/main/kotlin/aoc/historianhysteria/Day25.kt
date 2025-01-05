package aoc.historianhysteria

import aoc.utils.ChallengeDay
import aoc.utils.parts
import java.nio.file.Path
import kotlin.io.path.readLines

class Day25(private val input: List<String>) : ChallengeDay {

    constructor(path: Path) : this(path.readLines())

    /**
     * How many unique lock/key pairs fit together without overlapping in any column?
     */
    override fun part1(): Any {
        val (locks, keys) = input.parts { it }.partition { it.first().all { c -> c == '#' } }
        val lockHeights = locks.map(::heights)
        val keyHeights = keys.map(::heights)
        var count = 0
        for (lockH in lockHeights) {
            for (keyH in keyHeights) {
                if (lockH.zip(keyH).all { (l, k) -> l + k <= 5 }) {
                    count++
                }
            }
        }
        return count
    }

    private fun heights(strings: List<String>): IntArray = IntArray(strings[0].length) { col ->
        var height = 0
        for (row in 1 until strings.lastIndex) {
            if (strings[row][col] == '#') {
                height++
            }
        }
        height
    }

    override fun part2() = "Merry Christmas!"
}

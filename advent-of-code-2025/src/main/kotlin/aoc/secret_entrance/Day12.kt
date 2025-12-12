package aoc.secret_entrance

import aoc.utils.ChallengeDay
import aoc.utils.grid2d.dimension2D
import aoc.utils.parts
import java.nio.file.Path
import kotlin.io.path.readLines

class Day12(private val lines: List<String>) : ChallengeDay {
    constructor(path: Path) : this(path.readLines())

    override fun part1(): Int {
        return part1Elizarov()
    }

    /**
     * [Part 1 Elizarov](https://github.com/elizarov/AdventOfCode2025/blob/main/src/Day12.kt)
     *
     * This solution works for some larger inputs but not for the test input
     * as it only looks at the nr of # spots taken by each 3X3 and not the actual shapes.
     */
    private fun part1Elizarov(): Int {
        val inputParts = lines.parts { it }
        val pieceSize = inputParts.dropLast(1).mapIndexed { index, lines ->
            check(lines[0] == "$index:")
            val a = lines.drop(1)
            check(a.dimension2D() == dimension2D(3, 3))
            a.sumOf { it.count { c -> c == '#' } }
        }
        var ans = 0
        for (test in inputParts.last()) {
            val (m, n) = test.substringBefore(':').split('x').map { it.toInt() }
            val requiredCounts = test.substringAfter(": ").split(' ').map { it.toInt() }
            val total = requiredCounts.withIndex().sumOf { (i, required) -> pieceSize[i] * required }
            println("${m}x${n}: $total of ${m * n}, slack = ${m * n - total}")
            if (total <= m * n) ans++
        }
        return ans
    }

    override fun part2(): String = "Merry Christmas"
}

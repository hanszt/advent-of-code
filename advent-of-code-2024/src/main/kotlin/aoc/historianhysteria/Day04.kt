package aoc.historianhysteria

import aoc.utils.ChallengeDay
import aoc.utils.foldByPoint
import aoc.utils.model.GridPoint2D
import java.nio.file.Path
import kotlin.io.path.readText

class Day04(private val input: String) : ChallengeDay {
    constructor(path: Path) : this(path.readText())

    override fun part1(): Int {
        val target = "XMAS"
        val lines = input.lines()
        return lines.foldByPoint(0) { acc, x, y ->
            var result = acc
            for (dir in GridPoint2D.kingDirs) {
                val found = buildString {
                    for (k in target.indices) {
                        val dx = x + (dir.x * k)
                        val dy = y + (dir.y * k)
                        lines.getOrNull(dy)?.getOrNull(dx)?.let(::append) ?: break
                    }
                }
                if (found == target) {
                    result++
                }
            }
            result
        }
    }

    override fun part2(): Int {
        val target = "MAS"
        val lines = input.lines()
        return lines.foldByPoint(0) { acc, x, y ->
            var result = acc
            var r = 0
            for (dir in GridPoint2D.rookDirs) {
                if (r == 2) {
                    break
                }
                val found = buildString {
                    for (k in -1..1) {
                        val dx = x + (dir.x * k)
                        val dy = y + (dir.y * k)
                        lines.getOrNull(dy)?.getOrNull(dx)?.let(::append) ?: break
                    }
                }
                if (found == target) {
                    r++
                }
            }
            if (r == 2) {
                result++
            }
            result
        }
    }
}

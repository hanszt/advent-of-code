package aoc.historianhysteria

import aoc.utils.ChallengeDay
import aoc.utils.forEachPoint
import aoc.utils.model.GridPoint2D
import aoc.utils.model.GridPoint2D.Companion.northeast
import aoc.utils.model.GridPoint2D.Companion.northwest
import aoc.utils.model.GridPoint2D.Companion.southeast
import aoc.utils.model.GridPoint2D.Companion.southwest
import java.nio.file.Path
import kotlin.io.path.readText

class Day04(private val input: String) : ChallengeDay {
    constructor(path: Path) : this(path.readText())

    private companion object {
        val diagDirs = listOf(northwest, northeast, southeast, southwest)
    }

    override fun part1(): Int {
        var result = 0
        val target = "XMAS"
        val lines = input.lines()
        lines.forEachPoint { x, y ->
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
        }
        return result
    }

    override fun part2(): Int {
        var result = 0
        val target = "MAS"
        val lines = input.lines()
        lines.forEachPoint { x, y ->
            var r = 0
            for (dir in diagDirs) {
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
        }
        return result
    }
}

package aoc.snowrescuemission

import aoc.utils.ChallengeDay
import aoc.utils.model.GridPoint2D
import aoc.utils.model.gridPoint2D
import java.io.File

class Day03(
    fileName: String? = null,
    private val lines: List<String> = fileName?.let { File(it).readLines() } ?: emptyList()
) : ChallengeDay {

    override fun part1(): Int {
        var sum = 0
        for ((y, line) in lines.withIndex()) {
            var x = 0
            while (x < line.length) {
                val digit = buildString {
                    var cdx = line[x]
                    while (cdx.isDigit()) {
                        append(cdx)
                        cdx = line.getOrNull(++x) ?: break
                    }
                }
                sum += toPartNrOrZero(digit, y, x)
                x++
            }
        }
        return sum
    }

    private fun toPartNrOrZero(digit: String, y: Int, x: Int): Int {
        for (i in digit.indices) {
            for (dir in GridPoint2D.kingDirs) {
                lines.getOrNull(y + dir.y)?.getOrNull(x - digit.length + i + dir.x)?.let {
                    if (!it.isLetterOrDigit() && it != '.') {
                        return digit.toInt()
                    }
                }
            }
        }
        return 0
    }


    override fun part2(): Long {
        var sum = 0L
        for ((y, line) in lines.withIndex()) {
            for ((x, c) in line.withIndex()) {
                if (c == '*') {
                    val locations = findAdjacentNrs(x, y)
                    val isGear = locations.size == 2
                    if (isGear) {
                        val (pos1, pos2) = locations
                        sum += (toNr(pos1) * toNr(pos2))
                    }
                }
            }
        }
        return sum
    }

    private fun findAdjacentNrs(x: Int, y: Int): MutableList<GridPoint2D> {
        val positions = mutableListOf<GridPoint2D>()
        val nrs = HashSet<Int>()
        for (dir in GridPoint2D.kingDirs) {
            val dx = x + dir.x
            val dy = y + dir.y
            if (lines.getOrNull(dy)?.getOrNull(dx)?.isDigit() == true) {
                val gridPoint2D = gridPoint2D(dx, dy)
                val nr = toNr(gridPoint2D)
                if (nr !in nrs) {
                    nrs.add(nr)
                    positions += gridPoint2D
                }
            }
        }
        return positions
    }

    internal fun toNr(pos: GridPoint2D): Int = buildString {
        val nrLine = lines[pos.y]
        var dx = pos.x
        while (nrLine.getOrNull(dx)?.isDigit() == true) {
            dx--
        }
        var cNr = nrLine[++dx]
        while (cNr.isDigit()) {
            append(cNr)
            cNr = nrLine.getOrNull(++dx) ?: break
        }
    }.toInt()
}

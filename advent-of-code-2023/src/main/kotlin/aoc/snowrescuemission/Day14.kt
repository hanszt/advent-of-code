package aoc.snowrescuemission

import aoc.utils.ChallengeDay
import aoc.utils.grid2d.rotated
import aoc.utils.swap
import java.io.File

class Day14(
    fileName: String? = null,
    private val grid: List<String> = File(fileName ?: error("No fileName or text provided")).readLines()
) : ChallengeDay {

    override fun part1(): Int = grid.rotated().fold(0, ::addLoadOfColumn)
    override fun part2(): Int = day14part2(grid)

    private fun addLoadOfColumn(curLoad: Int, col: String): Int {
        var load = curLoad
        val column = CharArray(col.length) { col[it] }
        for (y in (col.lastIndex downTo 0)) {
            if (col[y] == ROUNDED_ROCK) {
                var dy = y
                do {
                    dy++
                    val cdy = column.getOrNull(dy) ?: break
                } while (cdy != CUBE_ROCKS && cdy != ROUNDED_ROCK)
                column.swap(y, dy - 1)
                load += dy
            }
        }
        return load
    }

    private companion object {
        private const val ROUNDED_ROCK = 'O'
        private const val CUBE_ROCKS = '#'
    }
}

package aoc

import aoc.utils.gridCount
import aoc.utils.mapByPoint
import aoc.utils.max
import aoc.utils.model.GridPoint2D
import aoc.utils.model.GridPoint2D.Companion.by
import aoc.utils.toIntGrid
import java.io.File

/**
 * @see <a href="https://adventofcode.com/2022/day/8">Day 8: Tree top treehouse</a>
 */
class Day08TreeTopTreeHouse(fileName: String) : ChallengeDay {

    private val treeGrid: Array<IntArray> = File(fileName).readLines().toIntGrid(Char::digitToInt)

    override fun part1(): Int = treeGrid.gridCount { x, y -> GridPoint2D.orthoDirs.any { visibleFromOutSide(x by y, it) } }

    private fun visibleFromOutSide(spot: GridPoint2D, delta: GridPoint2D): Boolean {
        val tree = treeGrid[spot.y][spot.x]
        var cur = spot
        while (cur.y in 1 until treeGrid.lastIndex && cur.x in 1 until treeGrid[0].lastIndex) {
            val other = treeGrid[cur.y + delta.y][cur.x + delta.x]
            if (other >= tree) return false
            cur += delta
        }
        return true
    }

    override fun part2(): Int =
        treeGrid.mapByPoint { x, y -> GridPoint2D.orthoDirs.map { toScore(x by y, it) }.reduce { total, score -> total * score } }
            .flatten()
            .max()

    private fun toScore(spot: GridPoint2D, delta: GridPoint2D): Int {
        var score = 0
        var cur = spot
        while (cur.y in 1 until treeGrid.lastIndex && cur.x in 1 until treeGrid[0].lastIndex) {
            val other = treeGrid[cur.y + delta.y][cur.x + delta.x]
            score++
            cur += delta
            if (other >= treeGrid[spot.y][spot.x]) break
        }
        return score
    }
}

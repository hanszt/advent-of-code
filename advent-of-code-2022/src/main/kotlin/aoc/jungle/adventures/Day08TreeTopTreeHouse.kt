package aoc.jungle.adventures

import aoc.utils.*
import aoc.utils.model.GridPoint2D
import java.io.File

/**
 * @see <a href="https://adventofcode.com/2022/day/8">Day 8: Tree top treehouse</a>
 */
class Day08TreeTopTreeHouse(fileName: String) : ChallengeDay {

    private val treeGrid = File(fileName).readLines().toIntGrid(Char::digitToInt)

    /**
     * How many trees are visible from outside the grid?
     */
    override fun part1(): Int = treeGrid.gridCount { s -> GridPoint2D.towerDirs.any { visibleFromOutSide(s, it) } }

    private fun visibleFromOutSide(spot: GridPoint2D, delta: GridPoint2D): Boolean {
        val tree = treeGrid[spot]
        var cur = spot
        while (true) {
            val other = treeGrid.getOrNull(cur + delta) ?: return true
            if (other >= tree) return false
            cur += delta
        }
    }

    /**
     * What is the highest scenic score possible for any tree?
     */
    override fun part2(): Int = treeGrid.gridMaxOf { p ->
        GridPoint2D.towerDirs.fold(1) { product, dir -> product * toScore(p, dir) }
    }

    private fun toScore(spot: GridPoint2D, delta: GridPoint2D): Int {
        val tree = treeGrid[spot]
        var score = 0
        var cur = spot
        do {
            val other = treeGrid.getOrNull(cur + delta) ?: break
            score++
            cur += delta
        } while (other < tree)
        return score
    }
}

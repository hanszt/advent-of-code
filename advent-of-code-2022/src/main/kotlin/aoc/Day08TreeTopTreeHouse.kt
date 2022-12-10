package aoc

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

    private val directions = listOf(0 by 1, 0 by -1, 1 by 0, -1 by 0)

    override fun part1(): Int {
        var counter = 0
        for (y in 1 until treeGrid.lastIndex) {
            for (x in 1 until treeGrid.first().lastIndex) {
                for (dir in directions) {
                    if (visibleFromOutSide(y, x, dir)) {
                        counter++
                        break
                    }
                }
            }
        }
        return counter + treeCountAtEdges()
    }

    private fun visibleFromOutSide(y: Int, x: Int, delta: GridPoint2D): Boolean {
        val tree = treeGrid[y][x]
        var xC = x
        var yC = y
        while (yC in 1 until treeGrid.lastIndex && xC in 1 until treeGrid[0].lastIndex) {
            val other = treeGrid[yC + delta.y][xC + delta.x]
            if (other >= tree) {
                return false
            }
            xC += delta.x
            yC += delta.y
        }
        return true
    }

    private fun treeCountAtEdges() = (2 * treeGrid.size + 2 * treeGrid.first().size - 4)

    override fun part2(): Int {
        val scores = ArrayList<Int>()
        for (y in treeGrid.indices) {
            for (x in 0 until treeGrid.first().size) {
                val y0 = toScore(y, x, directions[0])
                val y1 = toScore(y, x, directions[1])
                val x0 = toScore(y, x, directions[2])
                val x1 = toScore(y, x, directions[3])
                scores.add(x0 * x1 * y0 * y1)
            }
        }
        return scores.max()
    }

    private fun toScore(y: Int, x: Int, delta: GridPoint2D): Int {
        var score = 0
        var xC = x
        var yC = y
        while (yC in 1 until treeGrid.lastIndex && xC in 1 until treeGrid[0].lastIndex) {
            val other = treeGrid[yC + delta.y][xC + delta.x]
            xC += delta.x
            yC += delta.y
            score++
            if (other >= treeGrid[y][x]) {
                break
            }
        }
        return score
    }
}

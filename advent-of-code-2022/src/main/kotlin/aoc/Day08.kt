package aoc

import aoc.utils.max
import aoc.utils.toIntGrid
import java.io.File

/**
 * @see <a href="https://adventofcode.com/2022/day/8">Day 8: </a>
 */
class Day08(fileName: String) : ChallengeDay {

    private val treeGrid: Array<IntArray> = File(fileName).readLines().toIntGrid(Char::digitToInt)

    override fun part1(): Int {
        var counter = 0
        for (y in 1 until treeGrid.lastIndex) {
            for (x in 1 until treeGrid.first().lastIndex) {
                var visibleFromOutside = true
                val tree = treeGrid[y][x]
                var yC = y
                while (yC < treeGrid.lastIndex) {
                    val other = treeGrid[yC + 1][x]
                    yC++
                    if (other >= tree) {
                        visibleFromOutside = false
                        break
                    }
                }
                if (visibleFromOutside) {
                    counter++;
                    continue
                }
                visibleFromOutside = true
                yC = y
                while (yC > 0) {
                    val other = treeGrid[yC - 1][x]
                    yC--
                    if (other >= tree) {
                        visibleFromOutside = false
                        break
                    }
                }
                if (visibleFromOutside) {
                    counter++
                    continue
                }
                visibleFromOutside = true
                var xC = x
                while (xC < treeGrid.first().lastIndex) {
                    val other = treeGrid[y][xC + 1]
                    xC++
                    if (other >= tree) {
                        visibleFromOutside = false
                        break
                    }
                }
                if (visibleFromOutside) {
                    counter++
                    continue
                }
                visibleFromOutside = true
                xC = x
                while (xC > 0) {
                    val other = treeGrid[y][xC - 1]
                    xC--
                    if (other >= tree) {
                        visibleFromOutside = false
                        break
                    }
                }
                if (visibleFromOutside) {
                   counter++;
                }
            }
        }
        return counter + treeCountAtEdges()
    }

    private fun treeCountAtEdges() = (2 * treeGrid.size + 2 * treeGrid.first().size - 4)

    override fun part2(): Int {
        val scores = ArrayList<Int>()
        for (y in treeGrid.indices) {
            for (x in 0 until treeGrid.first().size) {
                val tree = treeGrid[y][x]
                var y0 = 0
                var yC = y
                while (yC < treeGrid.lastIndex) {
                    val other = treeGrid[yC + 1][x]
                    yC++
                    y0++
                    if (other >= tree) {
                        break
                    }
                }
                var y1 = 0
                yC = y
                while (yC > 0) {
                    val other = treeGrid[yC - 1][x]
                    yC--
                    y1++
                    if (other >= tree) {
                        break
                    }
                }
                var xC = x
                var x0 = 0
                while (xC < treeGrid.first().lastIndex) {
                    val other = treeGrid[y][xC + 1]
                    xC++
                    x0++
                    if (other >= tree) {
                        break
                    }
                }
                xC = x
                var x1 = 0
                while (xC > 0) {
                    val other = treeGrid[y][xC - 1]
                    xC--
                    x1++
                    if (other >= tree) {
                        break
                    }
                }
                scores.add(x0 * x1 * y0 * y1)
            }
        }
        return scores.max()
    }
}

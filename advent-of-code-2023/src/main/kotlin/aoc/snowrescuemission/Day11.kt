package aoc.snowrescuemission

import aoc.utils.ChallengeDay
import aoc.utils.invoke
import aoc.utils.model.longGridPoint2D
import aoc.utils.rotated
import java.io.File

class Day11(
    fileName: String? = null,
    image: String = File(fileName ?: error("No fileName or text provided")).readText()
) : ChallengeDay {

    private val grid = image.lines()


    override fun part1(): Long = findSumDistancesGalaxies()
    override fun part2(): Long = findSumDistancesGalaxies { it * (1_000_000L - 1) }

    internal fun findSumDistancesGalaxies(shiftMultiplier: (Int) -> Long = Int::toLong): Long {
        val rotated = grid.rotated()

        val rowsToExpand = grid.indices.filter { y -> grid[y].all { it == '.' } }
        val columnsToExpand = rotated.indices.filter { x -> rotated[x].all { it == '.' } }

        var counter = 0
        val grid = grid.map { r -> r.map { c -> if (c == '#') ++counter else 0 } }

        val galaxiesToLocations = buildMap {
            for ((y, row) in grid.withIndex()) {
                for ((x, c) in row.withIndex()) {
                    if (c != 0) {
                        val shiftX = shiftMultiplier(columnsToExpand.count { it < x })
                        val shiftY = shiftMultiplier(rowsToExpand.count { it < y })
                        this[c] = longGridPoint2D(x + shiftX, y + shiftY)
                    }
                }
            }
        }
        val combinations = buildSet {
            galaxiesToLocations.keys.forEach { k1 ->
                galaxiesToLocations.keys.forEach { k2 ->
                    if (k1 != k2) {
                        add(if (k1 < k2) k1 to k2 else k2 to k1)
                    }
                }
            }
        }
        return combinations.sumOf { (g1, g2) -> galaxiesToLocations(g1).manhattanDistance(galaxiesToLocations(g2)) }
    }

}

package aoc.snowrescuemission

import aoc.utils.ChallengeDay
import aoc.utils.invoke
import aoc.utils.model.gridPoint2D
import aoc.utils.rotated
import java.io.File

class Day11(
    fileName: String? = null,
    image: String = File(fileName ?: error("No fileName or text provided")).readText()
) : ChallengeDay {

    private val grid = image.lines()


    override fun part1(): Int {
        val rotated = grid.rotated()

        val rowsToExpand = grid.indices.filter { y -> grid[y].all { it == '.' } }
        val columnsToExpand = rotated.indices.filter { x -> rotated[x].all { it == '.' } }

        var counter = 0
        val grid = grid.mapTo(mutableListOf()) { r -> r.mapTo(mutableListOf()) { c -> if (c == '#') ++counter else 0 } }

        rowsToExpand.asReversed().forEach { grid.add(it, MutableList(grid[0].size) { 0 }) }
        columnsToExpand.asReversed().forEach { grid.forEach { c -> c.add(it, 0) } }

        val galaxiesToLocations = grid.asSequence()
            .mapIndexed { y, r -> r.mapIndexed { x, c -> gridPoint2D(x, y) to c } }
            .flatten()
            .filter { (_, c) -> c != 0 }
            .associate { it.second to it.first }

        val combinations = HashSet<Pair<Int, Int>>()
        galaxiesToLocations.keys.forEach { k1 ->
            galaxiesToLocations.keys.forEach { k2 ->
                if (k1 != k2) {
                    combinations.add(if (k1 < k2) k1 to k2 else k2 to k1)
                }
            }
        }
        return combinations.sumOf { (g1, g2) -> galaxiesToLocations(g1).manhattanDistance(galaxiesToLocations(g2)) }
    }

    override fun part2(): Int {
        TODO()
    }
}

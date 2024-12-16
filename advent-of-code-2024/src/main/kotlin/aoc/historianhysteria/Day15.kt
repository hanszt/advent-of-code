package aoc.historianhysteria

import aoc.utils.*
import aoc.utils.model.GridPoint2D
import aoc.utils.model.gridPoint2D
import java.nio.file.Path
import kotlin.io.path.readText

private const val robot = '@'

class Day15(input: String) : ChallengeDay {

    private val map: List<String>
    private val instructions: String

    init {
        val (map, instructions) = input.splitByBlankLine()
        this.map = map.lines()
        this.instructions = instructions.lines().joinToString("")
    }

    constructor(path: Path) : this(path.readText())

    /**
     * what is the sum of all boxes' GPS coordinates?
     */
    override fun part1(): Int {
        println("map = ${map.gridAsString()}")
        println("instructions = ${instructions}")
        var cur = map.findPoint { it == robot } ?: error("No start point found")
        var grid = map.toCharGrid()
        val m = grid[0].size
        val n = grid.size
        for (instruction in instructions) {
            val curGrid = grid.copyGrid()
            while (true) {
                val neighborPos = cur + GridPoint2D.right
                val neighbor = curGrid[neighborPos]
                when (neighbor) {
                    '.' -> grid.swap(cur, neighborPos).also { cur = neighborPos }
                    'O' -> {
                        var x = m - 2
                        do {
                            val c = grid[cur.y][x]
                            if (c == '.') {
                                grid.swap(p1x = x, p1y = cur.y, p2x = x - 1, p2y = cur.y).also { cur = gridPoint2D(x, cur.y) }
                            }
                            x--
                        } while (c != robot)
                    }
                }
                if ((cur.x..(m - 2)).all { grid[cur.y][it] != '.' }) {
                    break
                }
            }
            print(instruction + ", ")
        }
        TODO()
    }

    override fun part2(): Int = TODO()
}

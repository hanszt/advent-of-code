package aoc.historianhysteria

import aoc.utils.ChallengeDay
import aoc.utils.gridAsString
import aoc.utils.model.Dimension2D
import aoc.utils.model.dimension2D
import aoc.utils.model.gridPoint2D
import aoc.utils.set
import aoc.utils.toCharGrid
import java.nio.file.Path
import kotlin.io.path.readLines

class Day18(
    private val input: List<String>,
    private val nrOfFallenBytes: Int = 1_024,
    dimension2D: Dimension2D = dimension2D(71, 71)
) : ChallengeDay {

    private val memoryGrid = dimension2D.toCharGrid { '.' }.apply {
        input.take(nrOfFallenBytes).forEach {
            val point = it.split(',')
                .let { (x, y) -> gridPoint2D(x.toInt(), y.toInt()) }
            this[point] = '#'
        }
    }


    constructor(path: Path) : this(path.readLines())

    /**
     * Simulate the first kilobyte (1024 bytes) falling onto your memory space.
     * Afterward, what is the minimum number of steps needed to reach the exit?
     */
    override fun part1(): Int {
        println(memoryGrid.gridAsString())
        return -1
    }

    override fun part2(): Int = TODO()
}

package aoc.snowrescuemission

import aoc.utils.ChallengeDay
import java.nio.file.Path
import kotlin.io.path.readLines

class Day25(input: List<String>) : ChallengeDay {
    constructor(path: Path) : this(path.readLines())

    private val day25Abnew = Day25Abnew(input)

    /**
     * Find the three wires you need to disconnect in order to divide the components into two separate groups.
     * What do you get if you multiply the sizes of these two groups together?
     */
    override fun part1(): Int = day25Abnew.part1()

    override fun part2() = "Merry Christmas!"
}

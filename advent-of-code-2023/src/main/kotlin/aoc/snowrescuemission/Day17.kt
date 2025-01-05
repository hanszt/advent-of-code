package aoc.snowrescuemission

import aoc.utils.ChallengeDay
import java.nio.file.Path
import kotlin.io.path.readLines

/**
 * What is the least heat loss it can incur?
 *
 * [aoc.utils.Tag.PATH_SEARCH]
 */
class Day17(grid: List<String>) : ChallengeDay {
    constructor(path: Path) : this(path.readLines())
    private val day17Abnew = Day17Abnew(grid)

    override fun part1(): Int = day17Abnew.part1().best
    override fun part2(): Int = day17Abnew.part2().best
}

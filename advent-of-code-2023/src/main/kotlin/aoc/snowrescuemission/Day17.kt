package aoc.snowrescuemission

import aoc.utils.ChallengeDay
import java.nio.file.Path
import kotlin.io.path.readLines

class Day17(private val grid: List<String>) : ChallengeDay {
    constructor(path: Path) : this(path.readLines())

    /**
     * What is the least heat loss it can incur?
     */
    override fun part1(): Int = day17Part1(input = grid)

    /**
     * What is the least heat loss it can incur?
     */
    override fun part2(): Int = day17Part2(input = grid)
}

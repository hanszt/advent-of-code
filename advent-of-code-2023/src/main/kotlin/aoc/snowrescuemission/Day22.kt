package aoc.snowrescuemission

import aoc.utils.ChallengeDay
import java.nio.file.Path
import kotlin.io.path.readLines

class Day22(input: List<String>) : ChallengeDay {
    constructor(path: Path) : this(path.readLines())

    private val day22Zebalu = Day22Zebalu(input)

    /**
     * How many bricks could be safely chosen as the one to get disintegrated?
     */
    override fun part1(): Int = day22Zebalu.part1()

    /**
     * What is the sum of the number of other bricks that would fall?
     */
    override fun part2(): Int = day22Zebalu.part2()
}

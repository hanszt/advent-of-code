package aoc.historianhysteria

import aoc.utils.ChallengeDay
import java.nio.file.Path
import kotlin.io.path.readLines

class Day23(input: List<String>) : ChallengeDay {
    constructor(path: Path) : this(path.readLines())

    private val day23Zebalu = Day23Zebalu(input)

    /**
     * How many contain at least one computer with a name that starts with t?
     */
    override fun part1(): Int = day23Zebalu.part1()

    /**
     * What is the password to get into the LAN party?
     */
    override fun part2(): String = day23Zebalu.part2()
}

package aoc.historianhysteria

import aoc.utils.ChallengeDay
import aoc.utils.splitByBlankLine
import java.nio.file.Path
import kotlin.io.path.readText

class Day19(input: String) : ChallengeDay {


    init {
        val (f, s) = input.splitByBlankLine()
    }

    constructor(path: Path) : this(path.readText())

    /**
     * How many designs are possible?
     */
    override fun part1(): Int = TODO()

    override fun part2(): Int = TODO()
}

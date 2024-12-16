package aoc.historianhysteria

import aoc.utils.ChallengeDay
import java.nio.file.Path
import kotlin.io.path.readLines

class Day08(private val cityMap: List<String>) : ChallengeDay {

    constructor(text: String) : this(text.lines())
    constructor(path: Path) : this(path.readLines())

    /**
     * How many unique locations within the bounds of the map contain an antinode?
     */
    override fun part1(): Long {
        TODO()
    }

    override fun part2(): Int {
        TODO()
    }

}

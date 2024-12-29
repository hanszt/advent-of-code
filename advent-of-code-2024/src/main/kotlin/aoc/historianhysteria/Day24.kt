package aoc.historianhysteria

import aoc.utils.ChallengeDay
import aoc.utils.parts
import java.nio.file.Path
import kotlin.io.path.readLines


class Day24(input: List<String>) : ChallengeDay {
    constructor(path: Path) : this(path.readLines())

    private val day24Zebalu = Day24Zebalu(input)

    /**
     * What decimal number does it output on the wires starting with z?
     */
    override fun part1(): Any = day24Zebalu.part1()

    /**
     * What do you get if you sort the names of the eight wires involved in a swap and then join those names with commas?
     */
    override fun part2(): Any = day24Zebalu.part2()
}

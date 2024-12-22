package aoc.historianhysteria

import aoc.utils.ChallengeDay
import java.nio.file.Path
import kotlin.io.path.readLines

/**
 * What is the sum of the complexities of the five codes on your list?
 */
class Day21(private val input: List<String>) : ChallengeDay {

    override fun part1(): Long = day21Elizarov(input, k = 2)
    override fun part2(): Long = day21Elizarov(input, k = 25)
}

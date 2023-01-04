package aoc.spacesaga

import aoc.utils.ChallengeDay
import java.io.File

class Day01TheTyrannyOfTheRocketEquation(fileName: String) : ChallengeDay {

    private val lines = File(fileName).readLines()
    override fun part1(): Int = lines.sumOf { (it.toInt() / 3) - 2 }
    override fun part2(): Int = lines.sumOf { compoundFuel(it.toInt()) }

    fun compoundFuel(mass: Int): Int = generateSequence(mass) { (it / 3) - 2 }
        .drop(1)
        .takeWhile { it >= 0 }
        .sum()
}

package aoc.historianhysteria

import aoc.utils.ChallengeDay
import java.io.File

class Day03(fileName: String) : ChallengeDay {

    private val input = File(fileName).useLines { it.joinToString("") }

    internal companion object {
        val regexp1 by lazy { Regex("""mul\((\d+),(\d+)\)""") }
        val regexp2 by lazy { Regex("""mul\((\d+),(\d+)\)|don't\(\)|do\(\)""") }
    }

    override fun part1(): Int = regexp1
        .findAll(input)
        .sumOf(::toProduct)

    override fun part2(): Int {
        var result = 0
        var process = true
        for (mr in regexp2.findAll(input)) {
            if (mr.value == "do()") {
                process = true
                continue
            }
            if (mr.value == "don't()") {
                process = false
                continue
            }
            if (process) {
                result += toProduct(mr)
            }
        }
        return result
    }

    private fun toProduct(mr: MatchResult): Int = mr.groupValues.drop(1)
        .map { it.toInt() }
        .let { (d1, d2) -> d1 * d2 }
}

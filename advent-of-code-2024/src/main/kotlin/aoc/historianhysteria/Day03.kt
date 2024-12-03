package aoc.historianhysteria

import aoc.utils.ChallengeDay
import java.io.File

class Day03(
    fileName: String? = null,
    private val lines: List<String> = fileName?.let { File(it).readLines() } ?: error("No lines or fileName provided")
) : ChallengeDay {

    companion object {
        val regexp1 = Regex("mul\\((\\d{1,3},\\d{1,3})\\)")
        val regexp2 = Regex("(mul\\((\\d{1,3},\\d{1,3})\\)|don't\\(\\)|do\\(\\))")
    }

    override fun part1(): Int {
        val text = lines.joinToString("")
        return regexp1.findAll(text).sumOf {
            t ->
            toProduct(t)

        }
    }

    private fun toProduct(t: MatchResult): Int = t.groupValues.last()
        .split(',')
        .let { (d1, d2) -> d1.toInt() * d2.toInt() }

    data class Product(val n1: Int, val n2: Int)

    override fun part2(): Int {
        val text = lines.joinToString("")
        var result = 0
        var process = true
        for (mr in regexp2.findAll(text)) {
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
}

package aoc.snowrescuemission

import aoc.utils.ChallengeDay
import java.io.File

class Day01(
    fileName: String? = null,
    private val lines: List<String> = fileName?.let { File(it).readLines() } ?: emptyList()
) : ChallengeDay {

    override fun part1() = lines.sumOf { "${it.first(Char::isDigit)}${it.last(Char::isDigit)}".toInt() }
    override fun part2() = lines.sumOf(::part2IntFromFirstAndLastDigit)

    private val digitNames = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

    private fun part2IntFromFirstAndLastDigit(line: String): Int {
        val digits = mutableListOf<Int>()
        for ((i, c) in line.withIndex()) {
            if (c.isDigit()) {
                digits.add(c.digitToInt())
            }
            for ((j, name) in digitNames.withIndex()) {
                if (line.substring(i).startsWith(name)) {
                    digits.add(j + 1)
                }
            }
        }
        return "${digits.first()}${digits.last()}".toInt()
    }
}

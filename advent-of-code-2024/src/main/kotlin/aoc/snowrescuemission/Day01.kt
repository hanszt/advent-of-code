package aoc.snowrescuemission

import aoc.utils.ChallengeDay
import java.io.File

class Day01(
    fileName: String? = null,
    private val lines: List<String> = fileName?.let { File(it).readLines() } ?: error("No lines or fileName provided")
) : ChallengeDay {

    override fun part1() = TODO()
    override fun part2() = TODO()
}

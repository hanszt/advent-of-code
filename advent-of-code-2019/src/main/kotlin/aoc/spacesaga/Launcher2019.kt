package aoc.spacesaga

import aoc.utils.AocResult
import aoc.utils.ChallengeDay
import aoc.utils.GREEN
import aoc.utils.YELLOW
import aoc.utils.readTextFromResource
import aoc.utils.withColor

fun main(vararg args: String) {
    val inputDir = args.firstOrNull() ?: "advent-of-code-2019/input"
    println({}.readTextFromResource("/title.txt") withColor YELLOW)
    val results = sequenceOf(
        Day01TheTyrannyOfTheRocketEquation("$inputDir/day01.txt")
    ).flatMap(ChallengeDay::runParts)
        .onEach(::println)
        .toList()
    println("%nTotal solve time: %2.3f seconds%n".format(results.sumOf(AocResult::solveTimeNanos) / 1e9))
    println({}.readTextFromResource("/banner.txt") withColor GREEN)
}

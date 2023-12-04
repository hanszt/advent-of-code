package aoc.snowrescuemission

import aoc.utils.AocResult
import aoc.utils.BRIGHT_BLUE
import aoc.utils.ChallengeDay
import aoc.utils.GREEN
import aoc.utils.Launcher
import aoc.utils.readTextFromResource
import aoc.utils.withColor

fun main(vararg args: String) {
    println({}.readTextFromResource("/title.txt", orElse = "Advent of code 2023") withColor BRIGHT_BLUE)
    val inputDir = args.firstOrNull() ?: "advent-of-code-2023/input"
    val results = Launcher2023(inputDir).challengeDays
        .flatMap(ChallengeDay::runParts)
        .onEach(::println)
        .toList()

    println("%nTotal solve time: %2.3f seconds%n".format(results.sumOf(AocResult::solveTimeNanos) / 1e9))
    println({}.readTextFromResource("/banner.txt", orElse = "Powered by Kotlin and Java") withColor GREEN)
}

class Launcher2023(inputDir: String) : Launcher {

    internal val challengeDays = sequenceOf(
        Day01("$inputDir/day01.txt"),
        Day02("$inputDir/day02.txt"),
        Day03("$inputDir/day03.txt"),
        Day04("$inputDir/day04.txt"),
    )
    override fun challengeDays(): Iterable<ChallengeDay> = challengeDays.asIterable()

}

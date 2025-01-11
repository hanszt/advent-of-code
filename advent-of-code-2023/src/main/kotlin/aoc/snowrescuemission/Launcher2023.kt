package aoc.snowrescuemission

import aoc.utils.*
import aoc.utils.Colors.BRIGHT_BLUE
import aoc.utils.Colors.GREEN

fun main(vararg args: String) {
    println({}.readTextFromResource("/title.txt", orElse = "Advent of code 2023") withColor BRIGHT_BLUE)
    val inputDir = args.firstOrNull() ?: {}.relativeToResources(
        resourcePath = "/app-props.txt",
        rootFileName = "advent-of-code-2023",
        inputFileName = "input"
    )

    val results = Launcher2023(inputDir).challengeDays
        .flatMap(ChallengeDay::runParts)
        .onEach(::println)

    println("%nTotal solve time: %2.3f seconds%n".format(results.sumOf(AocResult::solveTimeNanos) / 1e9))
    println({}.readTextFromResource("/banner.txt", orElse = "Powered by Kotlin and Java") withColor GREEN)
}

class Launcher2023(inputDir: String) : Launcher {

    internal val challengeDays = sequenceOf(
        Day01("$inputDir/day01-dr.txt"),
        Day02("$inputDir/day02-dr.txt"),
        Day03("$inputDir/day03-dr.txt"),
        Day04("$inputDir/day04-dr.txt"),
        Day05("$inputDir/day05.txt"),
        Day08("$inputDir/day08-dr.txt"),
        Day09("$inputDir/day09.txt"),
        Day10("$inputDir/day10.txt"),
    )

    override fun challengeDays(): Iterable<ChallengeDay> = challengeDays.asIterable()

}

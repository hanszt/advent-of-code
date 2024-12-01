package aoc.snowrescuemission

import aoc.utils.*

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
        Day01("$inputDir/day01.txt"),
        Day02("$inputDir/day02.txt"),
        Day03("$inputDir/day03.txt"),
        Day04("$inputDir/day04.txt"),
        Day05("$inputDir/day05hzt.txt"),
        Day08("$inputDir/day08drillster.txt"),
        Day09("$inputDir/day09.txt"),
        Day10("$inputDir/day10.txt"),
    )

    override fun challengeDays(): Iterable<ChallengeDay> = challengeDays.asIterable()

}

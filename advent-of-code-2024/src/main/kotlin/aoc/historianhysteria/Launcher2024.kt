package aoc.historianhysteria

import aoc.utils.*
import kotlin.io.path.Path
import kotlin.time.Duration.Companion.nanoseconds

fun main(vararg args: String) {
    println({}.readTextFromResource("/title.txt", orElse = "Advent of code 2024") withColor BRIGHT_BLUE)
    val inputDir = args.firstOrNull() ?: {}.relativeToResources(
        resourcePath = "/app-props.txt",
        rootFileName = "advent-of-code-2024",
        inputFileName = "input"
    )
    val results = Launcher2023(inputDir).challengeDays
        .flatMap(ChallengeDay::runParts)
        .onEach(::println)

    println("%nTotal solve time: %s%n".format(results.sumOf(AocResult::solveTimeNanos).nanoseconds))
    println({}.readTextFromResource("/banner.txt", orElse = "Powered by Kotlin and Java") withColor GREEN)
}

class Launcher2023(inputDir: String) : Launcher {

    internal val challengeDays = sequenceOf(
        Day01("$inputDir/day01.txt"),
        Day02("$inputDir/day02-dr.txt"),
        Day03("$inputDir/day03-dr.txt"),
        Day04(Path("$inputDir/day04-dr.txt")),
        Day05(Path("$inputDir/day05-dr.txt")),
        Day06(Path("$inputDir/day06-dr.txt")),
        Day07(Path("$inputDir/day07-dr.txt")),
        Day08("$inputDir/day02-dr.txt"),
        Day09(Path("$inputDir/day02-dr.txt")),
        Day10(Path("$inputDir/day02-dr.txt")),
        Day11("$inputDir/day02-dr.txt"),
        Day12(Path("$inputDir/day02-dr.txt")),
        Day13(Path("$inputDir/day02-dr.txt")),
        Day14(Path("$inputDir/day02-dr.txt")),
    )

    override fun challengeDays(): Iterable<ChallengeDay> = challengeDays.asIterable()

}

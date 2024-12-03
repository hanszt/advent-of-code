package aoc.historianhysteria

import aoc.utils.*
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
    )

    override fun challengeDays(): Iterable<ChallengeDay> = challengeDays.asIterable()

}

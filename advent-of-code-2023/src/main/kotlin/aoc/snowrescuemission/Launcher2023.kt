package aoc.snowrescuemission

import aoc.utils.*
import aoc.utils.TextColor.Companion.BRIGHT_BLUE
import aoc.utils.TextColor.Companion.GREEN
import kotlin.io.path.Path

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
        { Day01("$inputDir/day01.txt") },
        { Day02("$inputDir/day02.txt") },
        { Day03("$inputDir/day03.txt") },
        { Day04("$inputDir/day04.txt") },
        { Day05(Path("$inputDir/day05.txt")) },
        { Day06("$inputDir/day06.txt") },
        { Day07("$inputDir/day07.txt") },
        { Day08("$inputDir/day08.txt") },
        { Day09("$inputDir/day09.txt") },
        { Day10("$inputDir/day10.txt") },
        { Day11("$inputDir/day11.txt") },
        { Day12("$inputDir/day12.txt") },
        { Day13("$inputDir/day13.txt") },
        { Day14("$inputDir/day14.txt") },
        { Day15(Path("$inputDir/day15.txt")) },
        { Day16("$inputDir/day16.txt") },
        { Day17(Path("$inputDir/day17.txt")) },
        { Day18("$inputDir/day18.txt") },
        { Day19(Path("$inputDir/day19.txt")) },
        { Day20("$inputDir/day20.txt") },
        { Day21(Path("$inputDir/day21.txt")) },
        { Day22(Path("$inputDir/day22.txt")) },
        { Day23(Path("$inputDir/day23.txt")) },
        { Day24(Path("$inputDir/day24.txt")) },
        { Day25(Path("$inputDir/day25.txt")) },
    ).mapNotNull { runCatching { it() }.onFailure { println(it.toString().withColor(TextColor.RED)) }.getOrNull() }

    override fun challengeDays(): Iterable<ChallengeDay> = challengeDays.asIterable()

}

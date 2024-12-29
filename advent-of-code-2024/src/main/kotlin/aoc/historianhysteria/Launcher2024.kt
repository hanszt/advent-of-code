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
    val results = Launcher2024(inputDir).challengeDays
        .flatMap(ChallengeDay::runParts)
        .onEach(::println)

    println("%nTotal solve time: %s%n".format(results.sumOf(AocResult::solveTimeNanos).nanoseconds))
    println({}.readTextFromResource("/banner.txt", orElse = "Powered by Kotlin and Java") withColor GREEN)
}

class Launcher2024(inputDir: String) : Launcher {

    internal val challengeDays = sequenceOf(
        { Day01(Path("$inputDir/day01.txt")) },
        { Day02(Path("$inputDir/day02.txt")) },
        { Day03(Path("$inputDir/day03.txt")) },
        { Day04(Path("$inputDir/day04.txt")) },
        { Day05(Path("$inputDir/day05.txt")) },
        { Day06(Path("$inputDir/day06.txt")) },
        { Day07(Path("$inputDir/day07.txt")) },
        { Day08(Path("$inputDir/day08.txt")) },
        { Day09(Path("$inputDir/day09.txt")) },
        { Day10(Path("$inputDir/day10.txt")) },
        { Day11(Path("$inputDir/day11.txt")) },
        { Day12(Path("$inputDir/day12.txt")) },
        { Day13(Path("$inputDir/day13.txt")) },
        { Day14(Path("$inputDir/day14.txt")) },
        { Day15(Path("$inputDir/day15.txt")) },
        { Day16(Path("$inputDir/day16.txt")) },
        { Day17(Path("$inputDir/day17.txt")) },
        { Day18(Path("$inputDir/day18.txt")) },
        { Day19(Path("$inputDir/day19.txt")) },
        { Day20(Path("$inputDir/day20.txt")) },
        { Day21(Path("$inputDir/day21.txt")) },
        { Day22(Path("$inputDir/day22.txt")) },
        { Day23(Path("$inputDir/day23.txt")) },
        { Day24(Path("$inputDir/day24.txt")) },
        { Day25(Path("$inputDir/day25.txt")) },
    ).mapNotNull { runCatching { it() }.onFailure { println(it) }.getOrNull() }

    override fun challengeDays(): Iterable<ChallengeDay> = challengeDays.asIterable()

}

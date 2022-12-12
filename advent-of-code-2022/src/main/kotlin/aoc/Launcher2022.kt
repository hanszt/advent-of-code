package aoc

import aoc.utils.*

fun main(vararg args: String) {
    println({}.readTextFromResource("/title.txt").withColor(BRIGHT_BLUE))

    val inputDir = args.firstOrNull() ?: "advent-of-code-2022/input"

    val results = sequenceOf(
        Day01CalorieCounting("$inputDir/day01.txt"),
        Day02RockPaperScissors("$inputDir/day02.txt"),
        Day03RucksackReorganisation("$inputDir/day03.txt"),
        Day04CampCleanup("$inputDir/day04.txt"),
        Day05SupplyStacks("$inputDir/day05.txt"),
        Day06TuningTrouble("$inputDir/day06.txt"),
        Day07NoSpaceLeftOnDevice("$inputDir/day07.txt"),
        Day08TreeTopTreeHouse("$inputDir/day08.txt"),
        Day09RopeBridge("$inputDir/day09.txt"),
        Day10CathodeRayTube("$inputDir/day10.txt"),
        Day11MonkeyInTheMiddle("$inputDir/day11.txt"),
        Day12("$inputDir/day12.txt"),
        Day13("$inputDir/day13.txt"),
        Day14("$inputDir/day14.txt"),
        Day15("$inputDir/day15.txt"),
        Day16("$inputDir/day16.txt"),
        Day17("$inputDir/day17.txt"),
        Day18("$inputDir/day18.txt"),
        Day19("$inputDir/day19.txt"),
        Day20("$inputDir/day20.txt"),
        Day21("$inputDir/day21.txt"),
        Day22("$inputDir/day22.txt"),
        Day23("$inputDir/day23.txt"),
        Day24("$inputDir/day24.txt"),
        Day25("$inputDir/day25.txt")
    ).flatMap(ChallengeDay::runParts)
        .onEach(::println)
        .toList()

    println("%nTotal solve time: %2.3f seconds%n".format(results.sumOf(AocResult::solveTimeNanos) / 1e9))
    println({}.readTextFromResource("/banner.txt").withColor(GREEN))
}

private val ansiColors = listOf(BRIGHT_BLUE, RESET, GREEN, RESET, YELLOW, RESET, CYAN, RESET)

data class AocResult(val name: String, val result: Result<String>, val solveTimeNanos: Long) {

    private val dayNr = name.slice(3..4).toInt()

    private val color = dayNr.toColor(ansiColors)

    private fun Int.toColor(colors: List<String>) = if (result.isSuccess) colors[this % colors.size] else RED

    override fun toString(): String = "%-40s Result: %-50s Solve time: %-7s"
        .format(name, result.getOrElse { "Failure: ${it.message}" }, solveTimeNanos.nanoTimeToFormattedDuration())
        .withColor(color)
}

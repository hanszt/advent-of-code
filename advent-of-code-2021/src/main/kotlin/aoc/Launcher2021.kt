package aoc

import aoc.utils.*

fun main(args: Array<String>) {
    ChallengeDay.inputDir = args.firstOrNull() ?: "advent-of-code-2021/input"
    println({}.readTextFromResource("/title.txt").withColor(RED))
    println("By Hans Zuidervaart")
    println("Credits to Roman Elizarov, The Turkey Dev and William Y Feng%n%n".format())
    val results = sequenceOf(
        Day01SonarSweep,
        Day02Dive,
        Day03BinaryDiagnostic,
        Day04GiantSquid,
        Day05HydrothermalVenture,
        Day06LanternFish,
        Day07TheTreacheryOfWales,
        Day08SevenSegmentSearch,
        Day09SmokeBasin,
        Day10SyntaxScoring,
        Day11DumboOctopus,
        Day12PassagePathing,
        Day13TransparentOrigami,
        Day14ExtendedPolymerization,
        Day15Chiton,
        Day16PacketDecoder,
        Day17TrickShot,
        Day18SnailFish,
        Day19BeaconScanner,
        Day20TrenchTrap,
        Day21DiracDice,
        Day22ReactorReboot,
        Day23Amphipod(),
        Day24ArithmeticLogicUnit,
        Day25SeaCucumber
    ).flatMap(ChallengeDay::runParts)
        .onEach(::println)
        .toList()
    println("%nTotal solve time: %2.3f seconds%n".format(results.sumOf(AocResult::solveTimeNanos) / 1e9))
    println({}.readTextFromResource("/banner.txt").withColor(GREEN))
}

private val ansiColors = listOf(BRIGHT_BLUE, RESET, GREEN, RESET, YELLOW, RESET, CYAN, RESET)

internal data class AocResult(val name: String, val result: Result<String>, val solveTimeNanos: Long) {

    private val dayNr = name.slice(3..4).toInt()

    private val color = dayNr.toColor(ansiColors)

    private fun Int.toColor(colors: List<String>) = if (result.isSuccess) colors[this % colors.size] else RED

    override fun toString(): String = "%-40s Result: %-50s Solve time: %-7s".withColor(color)
        .format(name, result.getOrElse { "Failure: ${it.message}" }, solveTimeNanos.nanoTimeToFormattedDuration())
}

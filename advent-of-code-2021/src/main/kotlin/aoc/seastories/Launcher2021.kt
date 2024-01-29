package aoc.seastories

import aoc.utils.AocResult
import aoc.utils.GREEN
import aoc.utils.RED
import aoc.utils.readTextFromResource
import aoc.utils.withColor

fun main(vararg args: String) {
    val inputDir = args.firstOrNull() ?: "advent-of-code-2021/input"
    println({}.readTextFromResource("/title.txt").withColor(RED))
    println("By Hans Zuidervaart")
    println("Credits to Roman Elizarov, The Turkey Dev and William Y Feng%n%n".format())

    val results = sequenceOf(
        Day01SonarSweep("$inputDir/day1.txt"),
        Day02Dive("$inputDir/day2.txt"),
        Day03BinaryDiagnostic("$inputDir/day3.txt"),
        Day04GiantSquid("$inputDir/day4.txt"),
        Day05HydrothermalVenture("$inputDir/day5.txt"),
        Day06LanternFish("$inputDir/day6.txt"),
        Day07TheTreacheryOfWales("$inputDir/day7.txt"),
        Day08SevenSegmentSearch("$inputDir/day8.txt"),
        Day09SmokeBasin("$inputDir/day9.txt"),
        Day10SyntaxScoring("$inputDir/day10.txt"),
        Day11DumboOctopus("$inputDir/day11.txt"),
        Day12PassagePathing("$inputDir/day12.txt"),
        Day13TransparentOrigami("$inputDir/day13.txt"),
        Day14ExtendedPolymerization("$inputDir/day14.txt"),
        Day15Chiton("$inputDir/day15.txt"),
        Day16PacketDecoder("$inputDir/day16.txt"),
        Day17TrickShot,
        Day18SnailFish("$inputDir/day18.txt"),
        Day19BeaconScanner("$inputDir/day19.txt"),
        Day20TrenchTrap("$inputDir/day20.txt"),
        Day21DiracDice("$inputDir/day21.txt"),
        Day22ReactorReboot("$inputDir/day22.txt"),
        Day23Amphipod("$inputDir/day23.txt"),
        Day24ArithmeticLogicUnit("$inputDir/day24.txt"),
        Day25SeaCucumber("$inputDir/day25.txt")
    ).flatMap(aoc.seastories.ChallengeDay::runParts)
        .onEach(::println)
        .toList()

    println("%nTotal solve time: %2.3f seconds%n".format(results.sumOf(AocResult::solveTimeNanos) / 1e9))
    println({}.readTextFromResource("/banner.txt").withColor(GREEN))
}

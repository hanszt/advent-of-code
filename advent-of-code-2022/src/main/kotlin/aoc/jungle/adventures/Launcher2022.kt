package aoc.jungle.adventures

import aoc.utils.*

fun main(vararg args: String) {
    println({}.readTextFromResource("/title.txt").withColor(BRIGHT_BLUE))
    val inputDir = args.firstOrNull() ?: "advent-of-code-2022/input"
    val results = Launcher2022(inputDir).challengeDaySequence()
        .flatMap(ChallengeDay::runParts)
        .onEach(::println)
        .toList()

    println("%nTotal solve time: %2.3f seconds%n".format(results.sumOf(AocResult::solveTimeNanos) / 1e9))
    println({}.readTextFromResource("/banner.txt").withColor(GREEN))
}

class Launcher2022(inputDir: String) : Launcher {

    private val challengeDays = sequenceOf(
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
        Day12HillClimbingAlgorithm("$inputDir/day12.txt"),
        Day13DistressSignal("$inputDir/day13.txt"),
        Day14RegolithReservoir("$inputDir/day14.txt"),
        Day15BeaconExclusionZone("$inputDir/day15.txt"),
        Day16ProboscideaVolcanium("$inputDir/day16.txt"),
        Day17PyroclasticFlow("$inputDir/day17.txt"),
        Day18BoilingBoulders("$inputDir/day18.txt"),
        Day19NotEnoughMinerals("$inputDir/day19.txt"),
        Day20GrovePositioningSystem("$inputDir/day20.txt"),
        Day21MonkeyMath("$inputDir/day21.txt"),
        Day22MonkeyMap("$inputDir/day22.txt"),
        Day23UnstableDiffusion("$inputDir/day23.txt"),
        Day24BlizzardBasin("$inputDir/day24.txt"),
        Day25FullOfHotAir("$inputDir/day25.txt")
    )
    override fun challengeDays(): Iterable<ChallengeDay> = challengeDays.asIterable()

    internal fun challengeDaySequence(): Sequence<ChallengeDay> = challengeDays

}

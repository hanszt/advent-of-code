package aoc

import utils.sumNaturalNrs
import java.io.File
import kotlin.math.abs

internal object Day07TheTreacheryOfWales : ChallengeDay {

    fun part1(path: String) = File(path).toMinimumConsumption { start, alignmentPos -> abs(start - alignmentPos) }

    fun part2(path: String) = File(path).toMinimumConsumption(::toFuelConsumptionPart2)

    private inline fun File.toMinimumConsumption(calculateConsumption: (Int, Int) -> Int): Int =
        readText().trim().split(',').map(String::toInt).run {
            (minOf { it }..maxOf { it })
                .map { alignmentPos -> sumOf { start -> calculateConsumption(start, alignmentPos) } }
                .minOf { it }
        }

    private fun toFuelConsumptionPart2(start: Int, alignmentPos: Int) = sumNaturalNrs(bound = abs(start - alignmentPos))

    override fun part1() = part1(ChallengeDay.inputDir + "/day7.txt")
    override fun part2() = part2(ChallengeDay.inputDir + "/day7.txt")
}

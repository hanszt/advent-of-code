package aoc.seastories

import aoc.utils.max
import aoc.utils.min
import aoc.utils.sumNaturalNrs
import java.io.File
import kotlin.math.abs

internal class Day07TheTreacheryOfWales(inputPath: String) : ChallengeDay {

    private val positions = File(inputPath).readText().trim().split(',').map(String::toInt)

    override fun part1() = positions.toMinimumConsumption { start, alignmentPos -> abs(start - alignmentPos) }
    override fun part2() = positions.toMinimumConsumption(::toFuelConsumptionPart2)

    private inline fun List<Int>.toMinimumConsumption(calculateConsumption: (Int, Int) -> Int): Int = (min()..max())
        .minOf { alignmentPos -> sumOf { start -> calculateConsumption(start, alignmentPos) } }

    private fun toFuelConsumptionPart2(start: Int, alignmentPos: Int) = sumNaturalNrs(bound = abs(start - alignmentPos))
}

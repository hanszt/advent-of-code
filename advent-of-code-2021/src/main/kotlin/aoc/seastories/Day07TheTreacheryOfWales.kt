package aoc.seastories

import aoc.utils.max
import aoc.utils.min
import aoc.utils.sumNaturalNrs
import java.io.File
import kotlin.math.abs

internal class Day07TheTreacheryOfWales(private val inputPath : String) : ChallengeDay {

    override fun part1() = File(inputPath).toMinimumConsumption { start, alignmentPos -> abs(start - alignmentPos) }

    override fun part2() = File(inputPath).toMinimumConsumption(::toFuelConsumptionPart2)

    private inline fun File.toMinimumConsumption(calculateConsumption: (Int, Int) -> Int): Int =
        readText().trim().split(',').map(String::toInt).run {
            (min()..max())
                .minOf { alignmentPos -> sumOf { start -> calculateConsumption(start, alignmentPos) } }
        }

    private fun toFuelConsumptionPart2(start: Int, alignmentPos: Int) = sumNaturalNrs(bound = abs(start - alignmentPos))
}

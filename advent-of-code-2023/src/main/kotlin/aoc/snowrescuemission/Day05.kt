package aoc.snowrescuemission

import aoc.utils.AocUtils
import aoc.utils.ChallengeDay
import java.nio.file.Path
import kotlin.io.path.readText

class Day05(input: String) : ChallengeDay {
    constructor(fileName: Path) : this(fileName.readText())

    private val seedSequence: Sequence<Long>
    private val mappingTexts: List<String>

    init {
        val input = input.split(AocUtils.DOUBLE_LINE_SEPARATOR)
        seedSequence = input[0].substring("seeds: ".length).splitToSequence(' ').map(String::toLong)
        mappingTexts = input.drop(1)
    }

    override fun part1(): Long {
        val forwardMappings = mappingTexts.map { buildMappings(it) { s, d -> s to d } }

        return seedSequence.minOf { seedNr -> forwardMappings.fold(seedNr, ::transform) }
    }

    override fun part2(): Long {
        val seedNrRanges = seedSequence
            .chunked(2)
            .map { (start, length) -> start..(start + length) }
            .toList()

        val reverseMappings = mappingTexts.reversed().map { buildMappings(it) { s, d -> d to s } }

        val initMinLocation = 0L
        return generateSequence(initMinLocation) { it + 1 }.first { location ->
            val seedNr = reverseMappings.fold(location, ::transform)
            seedNrRanges.any { seedNr in it }
        }
    }

    private fun transform(nr: Long, map: Map<LongRange, LongRange>): Long {
        for ((source, destination) in map) {
            if (nr in source) {
                return nr + (destination.first - source.first)
            }
        }
        return nr
    }

    private fun buildMappings(
        mappingAsText: String,
        associate: (LongRange, LongRange) -> Pair<LongRange, LongRange>
    ): Map<LongRange, LongRange> = mappingAsText.lineSequence()
        .drop(1) // drop title
        .associate {
            val (destStart, sourceStart, length) = it.split(' ').map(String::toLong)
            val sourceRange = sourceStart..(sourceStart + length)
            val destinationRange = destStart..(destStart + length)
            associate(sourceRange, destinationRange)
        }
}

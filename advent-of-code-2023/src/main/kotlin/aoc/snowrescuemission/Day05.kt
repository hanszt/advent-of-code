package aoc.snowrescuemission

import aoc.utils.ChallengeDay
import java.io.File

class Day05(
    fileName: String? = null,
    private val text: String = fileName?.let { File(it).readText() } ?: error("No text or fileName provided")
) : ChallengeDay {

    override fun part1(): Long {
        val input = text.split("\n\n")
        return input[0].substring("seeds: ".length).splitToSequence(' ')
            .map(String::toLong)
            .toDestination(input[1])
            .toDestination(input[2])
            .toDestination(input[3])
            .toDestination(input[4])
            .toDestination(input[5])
            .toDestination(input[6])
            .toDestination(input[7])
            .min()
    }

    override fun part2(): Long {
        val input = text.split("\n\n")
        val seedRanges = input[0].substring("seeds: ".length).splitToSequence(' ')
            .map(String::toLong)
            .chunked(2)
            .map { (start, length) -> start..(start + length) }
            .toList()

        val map1 = buildMappings(input[7]) { s, d -> d to s }
        val map2 = buildMappings(input[6]) { s, d -> d to s }
        val map3 = buildMappings(input[5]) { s, d -> d to s }
        val map4 = buildMappings(input[4]) { s, d -> d to s }
        val map5 = buildMappings(input[3]) { s, d -> d to s }
        val map6 = buildMappings(input[2]) { s, d -> d to s }
        val map7 = buildMappings(input[1]) { s, d -> d to s }

        return generateSequence(0L) { it + 1 }.first { location ->
            val source = location.toSource(map1)
                .toSource(map2)
                .toSource(map3)
                .toSource(map4)
                .toSource(map5)
                .toSource(map6)
                .toSource(map7)
            seedRanges.any { source in it }
        }
    }

    private fun Long.toSource(map: Map<LongRange, LongRange>): Long {
        for ((dest, source) in map) {
            if (this in dest) {
                return this + (source.first - dest.first)
            }
        }
        return this
    }

    private fun Sequence<Long>.toDestination(text: String): Sequence<Long> {
        val mappings = buildMappings(text) { s, d -> s to d }
        return this.map {
            for ((source, dest) in mappings.entries) {
                if (it in source) {
                    return@map it + (dest.first - source.first)
                }
            }
            it
        }
    }

    private fun buildMappings(
        text: String,
        association: (LongRange, LongRange) -> Pair<LongRange, LongRange>
    ): Map<LongRange, LongRange> = text.lineSequence()
        .drop(1) // drop title
        .associate {
            val (destStart, sourceStart, length) = it.split(' ').map(String::toLong)
            val sourceRange = sourceStart..(sourceStart + length)
            val destinationRange = destStart..(destStart + length)
            association(sourceRange, destinationRange)
        }
}

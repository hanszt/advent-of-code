package aoc.snowrescuemission

import aoc.utils.ChallengeDay
import java.io.File

class Day05(
    fileName: String? = null,
    private val text: String = fileName?.let { File(it).readText() } ?: error("No text or fileName provided")
) : ChallengeDay {

    override fun part1(): Long {
        val input = text.split("\n\n")
        val seeds = input[0].substring("seeds: ".length).splitToSequence(' ').map(String::toLong)
        return toMinimumLocation(seeds, input)
    }

    override fun part2(): Long {
        val input = text.split("\n\n")
        val seeds = input[0].substring("seeds: ".length).splitToSequence(' ')
            .map(String::toLong)
            .chunked(2)
            .flatMap { (start, length) -> start..(start + length) }
        return toMinimumLocation(seeds, input)
    }

    private fun toMinimumLocation(seeds: Sequence<Long>, input: List<String>): Long = seeds
        .convert(input[1])
        .convert(input[2])
        .convert(input[3])
        .convert(input[4])
        .convert(input[5])
        .convert(input[6])
        .convert(input[7])
        .min()

    private fun Sequence<Long>.convert(line: String): Sequence<Long> {
        val maps = line.lines().drop(1) // drop title
        val map = mutableMapOf<LongRange, LongRange>()
        for (table in maps) {
            val (destStart, sourceStart, length) = table.split(' ').map(String::toLong)
            val sourceRange = sourceStart..(sourceStart + length)
            val destinationRange = destStart..(destStart + length)
            map[sourceRange] = destinationRange
        }
        return this.map {
            for ((source, dest) in map.entries) {
                if (it in source) {
                    return@map it + (dest.first - source.first)
                }
            }
            it
        }
    }
}

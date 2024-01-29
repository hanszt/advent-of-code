package aoc.seastories

import aoc.seastories.model.SignalEntry
import aoc.utils.endsTo
import java.io.File

internal class Day08SevenSegmentSearch(private val inputPath: String) : ChallengeDay {

    override fun part1() = File(inputPath).readLines()
        .flatMap { toSignalEntry(it).fourDigitPatterns }
        .count { it.length in setOf(2, 3, 4, 7) }

    override fun part2() = File(inputPath).readLines()
        .map(::toSignalEntry)
        .sumOf(SignalEntry::decodeNumber)

    companion object {
        fun toSignalEntry(s: String) = s.split('|').map { it.trim().split(' ') }.endsTo(::SignalEntry)
    }
}

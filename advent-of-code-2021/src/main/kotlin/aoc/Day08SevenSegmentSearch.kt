package aoc

import model.SignalEntry
import utils.endsTo
import java.io.File

internal object Day08SevenSegmentSearch : ChallengeDay {

    fun part1(path: String) = File(path).readLines()
        .flatMap { toSignalEntry(it).fourDigitPatterns }
        .count { it.length in setOf(2, 3, 4, 7) }

    fun toSignalEntry(s: String) = s.split('|').map { it.trim().split(' ') }.endsTo(::SignalEntry)

    fun part2(path: String) = File(path).readLines()
        .map(::toSignalEntry)
        .sumOf(SignalEntry::decodeNumber)

    override fun part1() = part1(ChallengeDay.inputDir + "/day8.txt")
    override fun part2() = part2(ChallengeDay.inputDir + "/day8.txt")
}

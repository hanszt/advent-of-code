package aoc

import java.io.File

class Day06TuningTrouble(fileName: String) : ChallengeDay {

    private val dataStream = File(fileName).readText()

    private fun countBeforeStartOfMarker(length: Int) = dataStream.asSequence()
        .windowed(length)
        .withIndex()
        .first { allDifferent(it.value) }
        .index + length

    private fun allDifferent(window: List<Char>) = window.all(mutableSetOf<Char>()::add)

    override fun part1() = countBeforeStartOfMarker(4)
    override fun part2() = countBeforeStartOfMarker(14)
}

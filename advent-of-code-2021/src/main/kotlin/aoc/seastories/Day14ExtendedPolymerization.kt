package aoc.seastories

import aoc.utils.self
import aoc.utils.splitByBlankLine
import java.io.File

internal class Day14ExtendedPolymerization(private val inputPath: String) : ChallengeDay {

    override fun part1(): Long = solve(inputPath, 10)
    override fun part2(): Long = solve(inputPath, 40)

    private fun parseInput(path: String): Pair<List<Pair<String, Char>>, String> {
        val (polymerTemplate, insertions) = File(path).readText().splitByBlankLine()
        val instructions = insertions.lines()
            .map { it.split(" -> ").let { (pair, toBeInserted) -> pair to toBeInserted.first() } }
        return instructions to polymerTemplate
    }

    private fun solve(path: String, steps: Int) = parseInput(path)
        .let { (instructions, initPolymer) ->
            toPairCounts(instructions, initPolymer, steps)
                .toMaxAndMinCount(initPolymer.first())
                .let { (maxCount, minCount) -> maxCount - minCount }
        }

    private fun toPairCounts(instructions: List<Pair<String, Char>>, polymer: String, steps: Int): Map<String, Long> =
        (1..steps).fold(initPairCounts(polymer)) { pairCounts, _ -> applyStep(instructions, pairCounts) }

    private fun initPairCounts(polymer: String): MutableMap<String, Long> {
        val pairToCountMap = mutableMapOf<String, Long>()
        for (index in 0 until polymer.lastIndex) {
            val pair = polymer[index].toString() + polymer[index + 1].toString()
            pairToCountMap.merge(pair, 1, Long::plus)
        }
        return pairToCountMap
    }

    private fun applyStep(
        instructions: List<Pair<String, Char>>,
        pairToCountMap: Map<String, Long>
    ): MutableMap<String, Long> {
        val newPairToCountMap = mutableMapOf<String, Long>()
        for ((instruction, toInsert) in instructions) {
            val newPair1 = instruction.first().plus(toInsert.toString())
            val newPair2 = toInsert.toString() + instruction.last()
            pairToCountMap[instruction]?.let { count ->
                newPairToCountMap.merge(newPair1, count, Long::plus)
                newPairToCountMap.merge(newPair2, count, Long::plus)
            }
        }
        return newPairToCountMap
    }

    private fun Map<String, Long>.toMaxAndMinCount(first: Char): Pair<Long, Long> {
        val charToCountMap = mutableMapOf(first to 1L)
        for ((pair, pairCount) in this) {
            charToCountMap.merge(pair.last(), pairCount, Long::plus)
        }
        return charToCountMap.values.run { maxOf(::self) to minOf(::self) }
    }
}

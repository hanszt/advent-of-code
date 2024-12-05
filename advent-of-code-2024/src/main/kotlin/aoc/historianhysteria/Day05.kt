package aoc.historianhysteria

import aoc.utils.ChallengeDay
import aoc.utils.splitByBlankLine
import java.nio.file.Path
import kotlin.io.path.readText

class Day05(input: String) : ChallengeDay {

    private val orderingRules: Map<Int, MutableSet<Int>>
    private val updates: List<List<Int>>

    init {
        val (orderingRules, updates) = input.splitByBlankLine()
        this.orderingRules = buildMap {
            orderingRules.lineSequence()
                .map { it.split('|').let { it[0].toInt() to it[1].toInt() } }
                .forEach { (left, add) ->
                    computeIfAbsent(left) { HashSet() }.add(add)
                }
        }
        this.updates = updates.lineSequence()
            .map { it.splitToSequence(',').map(String::toInt).toList() }
            .toList()
    }

    constructor(path: Path) : this(path.readText())

    override fun part1(): Int = updates(correct = true).sumOf(::middle)

    override fun part2(): Int = updates(correct = false)
        .map(::correct)
        .sumOf(::middle)

    private fun middle(ints: List<Int>): Int = ints[ints.size / 2]

    private fun updates(correct: Boolean): Sequence<List<Int>> = sequence {
        for (update in updates) {
            var valid = true
            outer@ for ((i, entry) in update.withIndex()) {
                val requiredAfter = orderingRules[entry] ?: emptyList()
                for (e in requiredAfter) {
                    val j = update.indexOf(e)
                    if (j in 0..<i) {
                        valid = false
                        break@outer
                    }
                }
            }
            if (valid == correct) {
                yield(update)
            }
        }
    }

    private fun correct(ints: List<Int>): List<Int> = ints.asSequence()
        .sortedWith(::compareByOrderingRules)
        .toList()

    /**
     * Put item [i1] before item [i2], if item [i2] should be after item [i1] according to the rules.
     */
    private fun compareByOrderingRules(i1: Int, i2: Int): Int = if (orderingRules[i1]?.contains(i2) == true) -1 else 0
}

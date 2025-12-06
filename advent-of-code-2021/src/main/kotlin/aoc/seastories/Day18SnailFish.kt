package aoc.seastories

import org.hzt.graph.TreeNode
import java.io.File

/**
 * I've not been able to solve this day myself. This solution is from the repo Elizarov
 *
 * I did a little refactoring to understand what is going on.
 *
 * It is very educational to see such a clean and need solution. Many thanks to Elizarov for sharing his code on GitHub
 *
 * [aoc.utils.Tag.RECURSIVE], [aoc.utils.Tag.AOC_MATH]
 *
 * @see <a href="https://github.com/elizarov/AdventOfCode2021">Advent of code 2021 Elizarov</a>
 */
internal class Day18SnailFish(inputPath: String) : ChallengeDay {

    private val snailNrs = File(inputPath).useLines { it.map(SnailNr::parse).toList() }

    override fun part1(): Int = snailNrs
        .reduce { snailNr, other -> snailNr + other }
        .magnitude()

    override fun part2(): Int = snailNrs.findLargestSum()

    sealed class SnailNr : TreeNode<SnailNr> {

        private fun findPair(nesting: Int): Pair? {
            if (nesting == 0) return this as? Pair
            if (this is Pair) {
                left.findPair(nesting - 1)?.let { return it }
                right.findPair(nesting - 1)?.let { return it }
            }
            return null
        }

        private fun findRegular(limit: Int): Regular? = when (this) {
            is Regular -> takeIf { value >= limit }
            is Pair -> {
                left.findRegular(limit)?.let { return it }
                right.findRegular(limit)?.let { return it }
                null
            }
        }

        private fun traverse(keep: Pair): List<SnailNr> = when (this) {
            is Regular -> listOf(this)
            is Pair if this == keep -> listOf(this)
            is Pair -> left.traverse(keep) + right.traverse(keep)
        }

        private fun replace(map: Map<SnailNr, SnailNr>): SnailNr {
            map[this]?.let { return it }
            return when (this) {
                is Regular -> this
                is Pair -> Pair(left.replace(map), right.replace(map))
            }
        }

        private fun buildReplaceMap(): Map<SnailNr, SnailNr>? = findPair(4)?.let { pair ->
            check(pair.left is Regular)
            check(pair.right is Regular)
            buildMap {
                put(pair, Regular(0))
                val snailNrs = traverse(pair)
                val i = snailNrs.indexOf(pair)
                (snailNrs.getOrNull(i - 1) as? Regular)?.let { this[it] = Regular(it.value + pair.left.value) }
                (snailNrs.getOrNull(i + 1) as? Regular)?.let { this[it] = Regular(it.value + pair.right.value) }
            }
        } ?: findRegular(10)?.let {
            mapOf(it to Pair(Regular(it.value / 2), Regular((it.value + 1) / 2)))
        }

        operator fun plus(other: SnailNr): SnailNr = Pair(this, other).reduce()

        fun reduce() = generateSequence(seed = this, ::reductionStep).last()

        private fun reductionStep(snailNr: SnailNr): SnailNr? = snailNr.buildReplaceMap()?.let(snailNr::replace)

        fun magnitude(): Int = when (this) {
            is Regular -> value
            is Pair -> 3 * left.magnitude() + 2 * right.magnitude()
        }

        override fun childrenIterator(): Iterator<SnailNr> =
            (if (this is Pair) sequenceOf(left, right) else emptySequence()).iterator()

        companion object {
            fun parse(input: String): SnailNr {
                var cursor = 0
                fun parse(): SnailNr {
                    if (input[cursor] == '[') {
                        cursor++
                        val left = parse()
                        check(input[cursor++] == ',')
                        val right = parse()
                        check(input[cursor++] == ']')
                        return Pair(left, right)
                    }
                    val start = cursor
                    while (input[cursor] in '0'..'9') cursor++
                    return Regular(input.substring(start, cursor).toInt())
                }
                return parse().also { check(cursor == input.length) }
            }
        }
    }

    private class Regular(val value: Int) : SnailNr() {
        override fun toString(): String = value.toString()
    }

    private class Pair(val left: SnailNr, val right: SnailNr) : SnailNr() {
        override fun toString(): String = "[$left,$right]"
    }

    private fun List<SnailNr>.findLargestSum(): Int = sequence {
        val nrs = this@findLargestSum
        for (i in 0..lastIndex) {
            for (j in 0..lastIndex) {
                if (i != j) {
                    yield((nrs[i] + nrs[j]).magnitude())
                }
            }
        }
    }.max()
}

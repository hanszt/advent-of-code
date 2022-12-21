package aoc

import aoc.utils.max
import org.hzt.graph.TreeNode
import java.io.File

//
// I've not been able to solve this day myself. This solution is from the repo Elizarov
//
// I did a little refactoring to understand what is going on.
//
// It is very educational to see such a clean and need solution. Many thanks to Elizarov for sharing his code on GitHub
//
// source: https://github.com/elizarov/AdventOfCode2021
internal object Day18SnailFish : ChallengeDay {

    sealed class SnailNr : TreeNode<SnailNr, SnailNr> {

        private fun findPair(nesting: Int): Pair? {
            if (nesting == 0) return this as? Pair?
            if (this is Pair) {
                left.findPair(nesting - 1)?.let { return it }
                right.findPair(nesting - 1)?.let { return it }
            }
            return null
        }

        private fun findRegular(limit: Int): Regular? = when (this) {
            is Regular -> if (value >= limit) this else null
            is Pair -> {
                left.findRegular(limit)?.let { return it }
                right.findRegular(limit)?.let { return it }
                null
            }
        }

        private fun traverse(keep: Pair): List<SnailNr> = when (this) {
            is Regular -> listOf(this)
            is Pair -> if (this == keep) listOf(this) else left.traverse(keep) + right.traverse(keep)
        }

        private fun replace(fromSnailNrToSnailNrMap: Map<SnailNr, SnailNr>): SnailNr {
            fromSnailNrToSnailNrMap[this]?.let { return it }
            return when (this) {
                is Regular -> this
                is Pair -> Pair(left.replace(fromSnailNrToSnailNrMap), right.replace(fromSnailNrToSnailNrMap))
            }
        }

        private fun buildReplaceMap(): Map<SnailNr, SnailNr>? {
            val pair = findPair(4)
            if (pair != null) {
                check(pair.left is Regular)
                check(pair.right is Regular)
                val pairToRegularMap = mutableMapOf<SnailNr, SnailNr>(pair to Regular(0))
                val snailNrs = traverse(pair)
                val i = snailNrs.indexOf(pair)
                (snailNrs.getOrNull(i - 1) as? Regular)
                    ?.let { pairToRegularMap[it] = Regular(it.value + pair.left.value) }
                (snailNrs.getOrNull(i + 1) as? Regular)
                    ?.let { pairToRegularMap[it] = Regular(it.value + pair.right.value) }
                return pairToRegularMap
            }
            val regular = findRegular(10)
            if (regular != null) {
                val splitPair = Pair(Regular(regular.value / 2), Regular((regular.value + 1) / 2))
                return mapOf(regular to splitPair)
            }
            return null
        }

        operator fun plus(other: SnailNr): SnailNr = Pair(this, other).reduce()

        fun reduce() = generateSequence(seed = this, ::reductionStep).last()

        private fun reductionStep(snailNr: SnailNr): SnailNr? = snailNr.buildReplaceMap()?.let(snailNr::replace)

        fun magnitude(): Int = when (this) {
            is Regular -> value
            is Pair -> 3 * left.magnitude() + 2 * right.magnitude()
        }
    }

    private class Regular(val value: Int) : SnailNr() {
        override fun toString(): String = value.toString()

        override fun getChildren(): List<SnailNr> = emptyList();
    }

    private class Pair(val left: SnailNr, val right: SnailNr) : SnailNr() {
        override fun toString(): String = "[$left,$right]"

        override fun getChildren(): List<SnailNr> = listOf(left, right)
    }

    fun toSnailNr(snailNrAsString: String): SnailNr {
        var cursor = 0
        fun parse(): SnailNr {
            if (snailNrAsString[cursor] == '[') {
                cursor++
                val left = parse()
                check(snailNrAsString[cursor++] == ',')
                val right = parse()
                check(snailNrAsString[cursor++] == ']')
                return Pair(left, right)
            }
            val start = cursor
            while (snailNrAsString[cursor] in '0'..'9') cursor++
            return Regular(snailNrAsString.substring(start, cursor).toInt())
        }
        return parse().also { check(cursor == snailNrAsString.length) }
    }

    private fun List<SnailNr>.findLargestSum() = let { snailNrs ->
        snailNrs.indices.flatMap { index ->
            snailNrs.indices
                .filter { index != it }
                .map { (snailNrs[index] + snailNrs[it]).magnitude() }
        }.max()
    }

    fun part1(path: String): Int = File(path).readLines()
        .map(::toSnailNr)
        .reduce(SnailNr::plus)
        .magnitude()

    fun part2(path: String): Int = File(path).readLines()
        .map(::toSnailNr)
        .findLargestSum()

    override fun part1() = part1(ChallengeDay.inputDir + "/day18.txt")
    override fun part2() = part2(ChallengeDay.inputDir + "/day18.txt")
}

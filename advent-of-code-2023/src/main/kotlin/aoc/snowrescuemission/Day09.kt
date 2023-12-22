package aoc.snowrescuemission

import aoc.utils.ChallengeDay
import aoc.utils.mapLines
import aoc.utils.zipWithNextTo
import java.io.File

class Day09(fileName: String) : ChallengeDay {

    private val input = File(fileName).mapLines { it.splitToSequence(' ').map(String::toInt) }

    override fun part1(): Int = input.sumOf { it.extrapolate { next -> addLast(last() + next.last()) }.last() }
    override fun part2(): Int = input.sumOf { it.extrapolate { next -> addFirst(first() - next.first()) }.first() }

    private fun Sequence<Int>.extrapolate(addExtrapolated: MutableList<Int>.(List<Int>) -> Unit): List<Int> {
        val lists = mutableListOf(toMutableList())
        while (true) {
            val last = lists.last()
            if (last.all { it == 0 }) break
            lists += last.zipWithNextTo(ArrayList()) { c, n -> n - c }
        }
        for (i in lists.lastIndex downTo 1) {
            lists[i - 1].addExtrapolated(lists[i])
        }
        return lists.first()
    }
}
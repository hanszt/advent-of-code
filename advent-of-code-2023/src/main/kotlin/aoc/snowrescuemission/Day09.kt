package aoc.snowrescuemission

import aoc.utils.ChallengeDay
import java.io.File

class Day09(fileName: String) : ChallengeDay {

    private val sequences = File(fileName).useLines {
        it.map { line -> line.split(' ').map(String::toInt) }.toList()
    }

    override fun part1(): Int = sequences.sumOf(::extrapolate)
    override fun part2(): Int = sequences.sumOf(::extrapolatePart2)

    private fun extrapolate(start: List<Int>): Int {
        val sequences = buildDifSequences(start)
        for (i in sequences.lastIndex downTo 1) {
            val s = sequences[i - 1]
            val sNext = sequences[i]
            s.add(s.last() + sNext.last())
        }
        return sequences.first().last()
    }

    private fun extrapolatePart2(start: List<Int>): Int {
        val sequences = buildDifSequences(start)
        for (i in sequences.lastIndex downTo 1) {
            val s = sequences[i - 1]
            val sNext = sequences[i]
            s.add(0, (s.first() - sNext.first()))
        }
        return sequences.first().first()
    }

    private fun buildDifSequences(start: List<Int>): MutableList<MutableList<Int>> {
        val sequences = mutableListOf(start.toMutableList())
        while (!sequences.last().all { it == 0 }) {
            sequences += sequences.last().zipWithNext { c, n -> n - c }.toMutableList()
        }
        return sequences
    }
}
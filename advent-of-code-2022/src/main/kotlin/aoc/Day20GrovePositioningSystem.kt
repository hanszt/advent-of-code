package aoc

import java.io.File

class Day20GrovePositioningSystem(fileName: String) : ChallengeDay {

    private val input = File(fileName).readLines()

    override fun part1(): Long = day20part(1)
    override fun part2(): Long = day20part(2)

    private fun day20part(part: Int): Long {
        val decryptionKey = if (part == 1) 1L else 811589153L
        val rounds = if (part == 1) 1 else 10
        data class Number(var index: Int, val nr: Long)

        val numbers = input.mapIndexed { index, line -> Number(index, line.toLong() * decryptionKey) }
        val mutableNrs = numbers.toMutableList()
        repeat(rounds) {
            for (number in numbers) {
                val (index, nr) = number
                mutableNrs.removeAt(index)
                for (i in index until numbers.lastIndex) {
                    mutableNrs[i].index--
                }
                val j = (index + nr).mod(numbers.lastIndex)
                number.index = j
                mutableNrs.add(j, number)
                for (i in j + 1 until numbers.size) {
                    mutableNrs[i].index++
                }
            }
        }
        val firstIndex = mutableNrs.indexOfFirst { it.nr == 0L }
        return (1..3).sumOf { mutableNrs[(firstIndex + it * 1000) % numbers.size].nr }
    }
}

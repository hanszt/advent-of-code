package aoc.jungle.adventures

import aoc.utils.ChallengeDay
import java.io.File

/**
 * @see <a href="https://adventofcode.com/2022/day/10">Day 10: Cathode ray tube</a>
 */
class Day10CathodeRayTube(fileName: String) : ChallengeDay {

    private val programLines = File(fileName).readLines()

    override fun part1(): Int {
        val xValues = readProgram()
        return generateSequence(20) { it + 40 }.take(6).toList().sumOf { xValues[it - 1] * it }
    }

    override fun part2(): String = part2AsGrid().toExpectedTextOrElseThrow()

    internal fun part2AsGrid(): String {
        val xValues = readProgram()
        val crt = Array(6) { CharArray(40) }
        var cycle = 0
        for (y in crt.indices) {
            for (x in 0..<crt[0].size) {
                val xR = xValues[x + crt[0].size * y]
                crt[y][x] = if (x in xR - 1..xR + 1) '█' else '.'
                cycle++
            }
        }
        return crt.joinToString("\n") { it.joinToString("") }
    }

    private fun readProgram(): List<Int> {
        var x = 1
        val xValues = ArrayList<Int>()
        for (line in programLines) {
            val split = line.split(' ')
            val instruction = split[0]
            if ("noop" == instruction) {
                xValues.add(x)
            }
            val value = split.getOrNull(1)
            if (value != null) {
                xValues.add(x)
                xValues.add(x)
                x += value.toInt()
            }
        }
        return xValues
    }

    companion object {
        internal fun String.toExpectedTextOrElseThrow(): String {
            val expected = """
            ████.████.████..██..█..█...██..██..███..
            █.......█.█....█..█.█..█....█.█..█.█..█.
            ███....█..███..█....████....█.█..█.███..
            █.....█...█....█....█..█....█.████.█..█.
            █....█....█....█..█.█..█.█..█.█..█.█..█.
            ████.████.█.....██..█..█..██..█..█.███..
        """.trimIndent().trim()
            val text = "EZFCHJAB"
            if (this == expected) return text else {
                throw IllegalArgumentException("The expected grid for $text is: %n%n$expected%n%n but found %n%n${this}%n%n".format())
            }
        }
    }
}

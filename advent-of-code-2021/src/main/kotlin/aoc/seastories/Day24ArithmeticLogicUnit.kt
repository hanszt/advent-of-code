package aoc.seastories

import java.io.File
import kotlin.math.absoluteValue

//
// Thanks to William Y. Feng for explaining the solution: See https://www.youtube.com/watch?v=Eswmo7Y7C4U
//
// His repo can be visited here: https://github.com/womogenes/AoC-2021-Solutions/tree/main/day_24
//
//
internal object Day24ArithmeticLogicUnit : ChallengeDay {

    private fun createInputSpace(intProgression: IntProgression) = buildList {
        intProgression.forEach { i1 ->
            intProgression.forEach { i2 ->
                intProgression.forEach { i3 ->
                    intProgression.forEach { i4 ->
                        intProgression.forEach { i5 ->
                            intProgression.forEach { i6 ->
                                intProgression.forEach { i7 -> add(intArrayOf(i1, i2, i3, i4, i5, i6, i7)) }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun IntArray.toValidNr(incrementToRequired: List<Pair<Int?, Int?>>): IntArray? {
        var z = 0
        var digitsIndex = 0
        val result = IntArray(14)

        for ((index, pair) in incrementToRequired.withIndex()) {
            val (increment, modReq) = pair
            val constant = 26
            if (increment != null) {
                z = z * constant + this[digitsIndex] + increment
                result[index] = this[digitsIndex]
                digitsIndex += 1
            } else if (modReq != null) {
                result[index] = ((z % constant) - modReq)
                z /= constant
                if (result[index] !in 1..9) {
                    return null
                }
            } else throw IllegalStateException("increment or mod must be none null")
        }
        return result
    }

    fun part1(path: String) = solve(9 downTo 1, path)
    fun part2(path: String) = solve(1..9, path)

    override fun part1() = part1( ChallengeDay.inputDir + "/day24.txt")
    override fun part2() = part2( ChallengeDay.inputDir + "/day24.txt")

    private fun solve(intProgression: IntProgression, path: String): Long {
        val incrementToRequired = extractKeyValues(path)
        return createInputSpace(intProgression)
            .firstNotNullOf { it.toValidNr(incrementToRequired) }
            .joinToString("")
            .toLong()
    }

    private fun extractKeyValues(path: String): List<Pair<Int?, Int?>> {
        return File(path).readText()
            .split("inp")
            .filter(String::isNotBlank)
            .map(::incrementToRequired)
    }

    // these values are extracted from the input dataset.
    // See https://www.youtube.com/watch?v=Eswmo7Y7C4U for why these values where obtained
    private fun incrementToRequired(block: String): Pair<Int?, Int?> {
        val lines = block.lines().filter(String::isNotBlank)

        val increment = lines
            .lastOrNull { it.startsWith("add").and(it.last().isDigit()) }
            ?.split(" ")
            ?.last()
            ?.toInt()

        val required = lines.map { it.split(" ").last() }
            .mapNotNull(String::toIntOrNull)
            .firstOrNull { it < 0 }
            ?.absoluteValue

        return (if (required == null) increment else null) to required
    }
}

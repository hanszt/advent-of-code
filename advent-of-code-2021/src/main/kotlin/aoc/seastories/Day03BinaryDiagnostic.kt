package aoc.seastories

import java.io.File

internal class Day03BinaryDiagnostic(filePath: String) : ChallengeDay {

    private val lines = File(filePath).readLines()

    override fun part1(): Int = lines.calculatePowerConsumption()
    override fun part2(): Int = lines.run { toLiftSupportRating() * toCo2ScrubbingRating() }

    private fun List<String>.calculatePowerConsumption(): Int {
        val gammaRateBinary = sumOnesBinaryDigits().map { if (it * 2 > size) '1' else '0' }
        val gammaRate = gammaRateBinary.joinToString("").toInt(radix = 2)
        val epsilonRate = gammaRateBinary.map { if (it == '1') '0' else '1' }.joinToString("").toInt(radix = 2)
        return gammaRate * epsilonRate
    }

    private fun List<String>.sumOnesBinaryDigits(): List<Int> = map { binary -> binary.map(Char::digitToInt) }
        .reduce { result, curBinary -> result.indices.map { result[it] + curBinary[it]} }

    private fun List<String>.toLiftSupportRating() = toMutableList().calculate { ones, zeros -> ones >= zeros }

    private fun List<String>.toCo2ScrubbingRating() = toMutableList().calculate { ones, zeros -> ones < zeros }

    private inline fun MutableList<String>.calculate(biPredicate: (Int, Int) -> Boolean): Int {
        var index = 0
        while (size > 1) {
            val sumAtIndex = sumOnesBinaryDigits()[index]
            val binaryDigit = if (biPredicate(sumAtIndex * 2, size)) '1' else '0'
            removeIf { it[index] != binaryDigit }
            index++
        }
        return first().toInt(radix = 2)
    }
}

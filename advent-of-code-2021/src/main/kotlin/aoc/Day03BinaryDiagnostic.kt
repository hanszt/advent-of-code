package aoc

import java.io.File

internal object Day03BinaryDiagnostic : ChallengeDay {

    fun part1(path: String): Int = File(path).readLines()
        .map(String::toCharArray)
        .calculatePowerConsumption()

    private fun List<CharArray>.calculatePowerConsumption(): Int {
        val gammaRateBinary = sumOnesBinaryDigits().map { if (it * 2 > size) '1' else '0' }
        val gammaRate = gammaRateBinary.joinToString("").toInt(radix = 2)
        val epsilonRate = gammaRateBinary.map { if (it == '1') '0' else '1' }.joinToString("").toInt(radix = 2)
        return gammaRate * epsilonRate
    }

    private fun List<CharArray>.sumOnesBinaryDigits(): List<Int> = map { binary -> binary.map(Char::digitToInt) }
        .reduce { result, curBinary -> result.indices.map { result[it] + curBinary[it]} }

    fun part2(path: String): Int = File(path).readLines()
        .map(String::toCharArray)
        .run { toLiftSupportRating() * toCo2ScrubbingRating() }

    private fun List<CharArray>.toLiftSupportRating() = toMutableList().calculate { ones, zeros -> ones >= zeros }

    private fun List<CharArray>.toCo2ScrubbingRating() = toMutableList().calculate { ones, zeros -> ones < zeros }

    private inline fun MutableList<CharArray>.calculate(biPredicate: (Int, Int) -> Boolean): Int {
        var index = 0
        while (size > 1) {
            val sumAtIndex = sumOnesBinaryDigits()[index]
            val binaryDigit = if (biPredicate(sumAtIndex * 2, size)) '1' else '0'
            removeIf { it[index] != binaryDigit }
            index++
        }
        return first().joinToString("").toInt(radix = 2)
    }

    override fun part1() = part1(ChallengeDay.inputDir + "/day3.txt")
    override fun part2() = part2(ChallengeDay.inputDir + "/day3.txt")
}

package model

import utils.mapBoth
import utils.mapFirst

internal data class SignalEntry(val uniqueSignalPatterns: List<String>, val fourDigitPatterns: List<String>) {

    fun decodeNumber(): Int = patternNrsRepresentedByIndex()
        .let { patterns -> fourDigitPatterns.joinToString("") { it.decodeToDigit(patterns).toString() }.toInt() }

    private fun String.decodeToDigit(patterns: List<String>): Int =
        patterns.find { (length == it.length) and it.all(this::contains) }.let(patterns::indexOf)

    // A representation of all the segments labeled with nr 0 to 6.
    //      0  xxxx
    //      1 x    x 2
    //        x    x
    //      3  xxxx
    //      4 x    x 5
    //        x    x
    //      6  xxxx
    fun patternNrsRepresentedByIndex(): List<String> {
        val one = uniqueSignalPatterns.first { it.length == 2 }
        val four = uniqueSignalPatterns.first { it.length == 4 }
        val seven = uniqueSignalPatterns.first { it.length == 3 }
        val eight = uniqueSignalPatterns.first { it.length == 7 }

        val twoThreeFive = uniqueSignalPatterns.filter { it.length == 5 }
        val zeroSixNine = uniqueSignalPatterns.filter { it.length == 6 }

        val (three, twoFive) = twoThreeFive.partition { one.all(it::contains) }.mapFirst(List<String>::single)
        val (nine, zeroSix) = zeroSixNine.partition { three.all(it::contains) }.mapFirst(List<String>::single)

        val segmentOneAndThree = four.filterNot { it in one }
        val segment1Char = nine.first { it !in three }
        val segment3Char = segmentOneAndThree.first { it != segment1Char }

        val (five, two) = twoFive.partition { segment1Char in it }.mapBoth(List<String>::single)
        val (six, zero) = zeroSix.partition { segment3Char in it }.mapBoth(List<String>::single)

        return listOf(zero, one, two, three, four, five, six, seven, eight, nine)
    }
}

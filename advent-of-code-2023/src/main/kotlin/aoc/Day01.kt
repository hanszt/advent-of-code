package aoc

import aoc.utils.ChallengeDay
import java.io.File

class Day01(
    fileName: String? = null,
    private val lines: List<String> = fileName?.let { File(it).readLines() } ?: emptyList()
) : ChallengeDay {

    override fun part1(): Int = lines.sumOf { "${it.first(Char::isDigit)}${it.last(Char::isDigit)}".toInt() }

    override fun part2(): Int = lines.sumOf(::toFirstAndLastDigit)

    private fun toFirstAndLastDigit(s: String): Int {
        data class FirstAndLast(
            val first: IndexedValue<Int> = IndexedValue(Int.MAX_VALUE, 0),
            val last: IndexedValue<Int> = IndexedValue(Int.MIN_VALUE, 0)
        )

        val (firstInText, lastInText) = s.findAllOccurrencesOf(namesToDigits.keys)
            .mapNotNull { namesToDigits[it]?.let { digit -> it to digit } }
            .fold(FirstAndLast()) { result, (name, digit) ->
                val first = IndexedValue(s.indexOf(name), digit)
                val last = IndexedValue(s.lastIndexOf(name), digit)
                FirstAndLast(
                    first = if (result.first.index < first.index) result.first else first,
                    last = if (result.last.index > last.index) result.last else last
                )
            }

        val firstDigit = s.withIndex().filter { it.value.isDigit() }.minByOrNull { it.index } ?: IndexedValue(Int.MAX_VALUE, '0')
        val lastDigit = s.withIndex().filter { it.value.isDigit() }.maxByOrNull { it.index } ?: IndexedValue(Int.MIN_VALUE, '0')

        val first = if (firstInText.index < firstDigit.index) firstInText.value else firstDigit.value.digitToInt()
        val last = if (lastInText.index > lastDigit.index) lastInText.value else lastDigit.value.digitToInt()
        return "$first$last".toInt()
    }

    private val namesToDigits = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9,
    )

    private fun String.findAllOccurrencesOf(strings: Collection<String>): Sequence<String> = sequence {
        for (i in indices) {
            var s = this@findAllOccurrencesOf[i].toString()
            if (s in strings) {
                yield(s)
            }
            for (j in i + 1..<length) {
                s += this@findAllOccurrencesOf[j]
                if (s in strings) {
                    yield(s)
                }
            }
        }
    }
}

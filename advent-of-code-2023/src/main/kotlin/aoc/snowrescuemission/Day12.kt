package aoc.snowrescuemission

import aoc.utils.ChallengeDay
import java.io.File

class Day12(
    fileName: String? = null,
    lines: List<String> = File(fileName ?: error("No fileName or text provided")).readLines()
) : ChallengeDay {

    private val records = lines.map {
        val (r1, r2) = it.split(' ')
        Record(r1, r2.split(',').map(String::toInt))
    }

    private data class Record(val conditionRecord: String, val referenceCounts: List<Int>) {

        /**
         * Build tree of possibilities and put them in a set.
         * At the end return size of the set with all valid arrangements
         *
         *
         *
         *
         */
        fun arrangementCount(): Int {
            val possibleArrangements: MutableSet<String> = HashSet()
            for (counts in referenceCounts) {
                TODO()
            }
            return possibleArrangements.size
        }

        fun arrangementCount(cur: String, possibleArrangements: MutableSet<String> = HashSet()): Int {
            for ((i, c) in conditionRecord.withIndex()) {
                if (c == UNKNOWN) {
                    val chars = conditionRecord.toCharArray()
                    chars[i] = OPERATIONAL
                    val next = chars.joinToString(separator = "")
                    if (isValid(next)) {
                        possibleArrangements.add(next)
                        arrangementCount(next, possibleArrangements)
                    }
                }
            }
            return possibleArrangements.size
        }

        fun isValid(arrangement: String): Boolean {
            if (UNKNOWN in arrangement) return false
            val damagedCount = arrangement.count { it == DAMAGED }
            if (damagedCount != referenceCounts.sum()) return false
            for (ref in referenceCounts) {
                TODO()
            }
            return true
        }

        companion object {
            private const val UNKNOWN = '?'
            private const val DAMAGED = '#'
            private const val OPERATIONAL ='.'
        }
    }

    override fun part1(): Int = records.onEach { println(it) }.sumOf(Record::arrangementCount)

    override fun part2(): Any {
        TODO("Not yet implemented")
    }
}

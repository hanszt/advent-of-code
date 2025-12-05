package aoc.secret_entrance

import aoc.utils.ChallengeDay
import aoc.utils.mapLines
import aoc.utils.splitByBlankLine
import java.nio.file.Path
import kotlin.io.path.readText
import kotlin.math.max

class Day05(text: String) : ChallengeDay {

    private val freshIngredientIdRanges: List<LongRange>
    private val availableIngredientIds: List<Long>

    init {
        val (ranges, ids) = text.splitByBlankLine()
        freshIngredientIdRanges = ranges.mapLines {
            it.split('-').let { (a, b) -> a.toLong()..b.toLong() }
        }
        availableIngredientIds = ids.mapLines { it.toLong() }
    }

    constructor(path: Path) : this(path.readText())

    override fun part1(): Int {
        return availableIngredientIds.count { id -> freshIngredientIdRanges.any { range -> id in range } }
    }

    override fun part2(): Long {
        val merged = mergeRanges(freshIngredientIdRanges)
        // Sum all the merged ranges. Example: Range 3..5 contains 3, 4, 5 (Length is 5 - 3 + 1 = 3)
        return merged.sumOf { (it.last - it.first) + 1 }
    }

    companion object {

        /**
         * Examples:
         *
         * Mergeable:
         * 1..6 <-> 6..11
         * 6..11 <-> 6..7
         * 1..6 <-> 7..11
         * 1..10 <-> 3..4
         *
         * Non-mergeable:
         * 1..6 <-> 8..11
         * 8..10 <-> 1..5
         */
        internal fun mergeRanges(inputRanges: List<LongRange>): List<LongRange> {
            if (inputRanges.size <= 1) return inputRanges
            // Sort ranges by their start value
            val ranges = inputRanges.sortedBy { it.first }
            val merged = buildList {
                var start = ranges[0].first
                var end = ranges[0].last

                for (i in 1 until ranges.size) {
                    val nextStart = ranges[i].first
                    val nextEnd = ranges[i].last
                    // Check if the next range overlaps or touches the current range.
                    // handles cases like 3-5 and 6-10, which are contiguous.
                    if (nextStart <= end + 1) {
                        // Merge: extend the current end to the max of both
                        end = max(end, nextEnd)
                    } else {
                        // No overlap: save the current range and start a new one
                        add(start..end)
                        start = nextStart
                        end = nextEnd
                    }
                }
                // Add the final range
                add(start..end)
            }
            return merged
        }
    }
}

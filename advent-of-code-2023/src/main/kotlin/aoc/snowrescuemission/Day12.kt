package aoc.snowrescuemission

import aoc.utils.ChallengeDay
import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * https://github.com/zebalu/advent-of-code-2023/blob/master/aoc2023/src/main/java/io/github/zebalu/aoc2023/days/Day12.java
 *
 * [aoc.utils.Tag.RECURSIVE]
 */
class Day12(lines: List<String>) : ChallengeDay {
    constructor(fileName: String) : this(Path(fileName).readLines())

    private val arrangements = lines.map {
        val parts = it.split(' ')
        Arrangement(
            desc = parts[0],
            nums = parts[1].split(',').map { it.toInt() }
        )
    }

    /**
     * For each row, count all the different arrangements of operational and broken springs that meet the given criteria.
     * What is the sum of those counts?
     */
    override fun part1(): Long = arrangements.sumOf { count(it) }

    /**
     * Unfold your condition records; what is the new sum of possible arrangement counts?
     */
    override fun part2(): Long = arrangements.sumOf { count(it.unFold(5)) }

    @JvmRecord
    private data class Arrangement(val desc: String, val nums: List<Int>) {
        fun unFold(folds: Int): Arrangement {
            val newNums: MutableList<Int> = ArrayList<Int>()
            val nc = buildString {
                for (i in 0..<folds) {
                    for (j in desc.indices) {
                        append(desc[j])
                    }
                    newNums.addAll(nums)
                    if (i < folds - 1) {
                        append('?')
                    }
                }
            }
            return Arrangement(desc = nc, nums = newNums)
        }

        fun countRequiredLengthFrom(group: Int): Int = (group..<nums.size).sumOf { nums[it] + 1 }
    }

    // credit: https://github.com/p-kovacs/advent-of-code-2023/blob/master/src/main/java/com/github/pkovacs/aoc/y2023/Day12.java
    private fun count(arrangement: Arrangement, fieldIndex: Int = 0, groupIndex: Int = 0, cache: MutableMap<Long, Long> = HashMap()): Long {
        cache[toKey(fieldIndex.toLong(), groupIndex.toLong())]?.let { return it }
        if (groupIndex == arrangement.nums.size) {
            return (if (arrangement.desc.slice(fieldIndex..<arrangement.desc.length).none { it == '#' }) 1 else 0).toLong()
        }
        var count: Long = 0
        val length = arrangement.nums[groupIndex]
        val maxPos = arrangement.desc.length - arrangement.countRequiredLengthFrom(groupIndex) + 1
        var canContinue = true
        var i = fieldIndex
        while (i <= maxPos && canContinue) {
            when {
                i > fieldIndex && arrangement.desc[i - 1] == '#' -> canContinue = false
                arrangement.desc.slice(i..<i + length).none { it == '.' }
                        && (i == arrangement.desc.length - length || arrangement.desc[i + length] != '#')
                    -> count += count(arrangement, fieldIndex = i + length + 1, groupIndex = groupIndex + 1, cache)
            }
            i++
        }
        cache[toKey(fieldIndex.toLong(), groupIndex.toLong())] = count
        return count
    }

    private fun toKey(fieldIndex: Long, groupIndex: Long): Long = (fieldIndex shl 32) or groupIndex
}

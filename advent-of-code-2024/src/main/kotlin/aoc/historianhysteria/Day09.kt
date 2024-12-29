package aoc.historianhysteria

import aoc.utils.ChallengeDay
import java.nio.file.Path
import java.util.TreeSet
import kotlin.collections.lastIndex
import kotlin.io.path.readText

/**
 * What is the resulting filesystem checksum?
 *
 * I had an of by one error, looked at the following to see what it had to be.
 * https://github.com/elizarov/AdventOfCode2024/blob/main/src/Day09_1.kt to see what the right answer should have been
 */
class Day09(private val diskMap: String) : ChallengeDay {
    constructor(path: Path) : this(path.readText())

    private val diskSegments by lazy { toSegments(diskMap) }

    private fun toSegments(diskMap: String): List<DiskSegment> {
        var index = 0L
        val segments = diskMap.flatMapIndexed { i, c ->
            val size = c.digitToInt()
            val space = if (i % 2 == 0) (0..<size).map { FileSegment(i / 2) }
            else (0..<size).map { EmptySegment }
            index += size
            space
        }
        return segments
    }

    override fun part1(): Long {
        val deFragmented = deFragment(diskSegments)
        var sum = 0L
        for ((i, fs) in deFragmented.withIndex()) {
            sum += (i * fs.id).toLong()
        }
        return sum
    }

    /**
     * Solution Elizarov
     */
    override fun part2(): Long {
        val input = diskMap.lines()
        val s = input[0]
        val m = s.sumOf { it.digitToInt() }
        val a = IntArray(m)
        var cur = 0
        data class BD(val j: Int, val k: Int): Comparable<BD> {
            override fun compareTo(other: BD): Int = j - other.j
        }
        val files = ArrayList<BD>()
        val space = Array(10) { TreeSet<BD>() }
        for (i in s.indices) {
            val k = s[i].digitToInt()
            val t = if (i % 2 == 0) {
                files += BD(cur, k)
                i / 2
            } else {
                if (k > 0) space[k].add(BD(cur, k))
                -1
            }
            repeat(k) { a[cur++] = t }
        }
        check(cur == m)
        for ((fj, fl) in files.reversed()) {
            var best: BD? = null
            for (k in fl..9) {
                if (space[k].isNotEmpty() && (best == null || space[k].first().j < best.j)) best = space[k].first()
            }
            if (best == null) continue
            val (sj, sl) = best
            if (sj > fj) continue
            for (k in 0..<fl) {
                a[fj + k] = a[sj + k].also { a[sj + k] = a[fj + k] }
            }
            space[sl].remove(best)
            val rem = sl - fl
            if (rem > 0) space[rem] += BD(sj + fl, rem)
        }
        var sum = 0L
        for (j in 0..<m) {
            if (a[j] != -1) sum += a[j] * j
        }
        return sum
    }

    private fun deFragment(memory: List<DiskSegment>): List<FileSegment> = buildList {
        var i1 = 0
        var i2 = memory.lastIndex
        while (i1 <= i2) {
            val segment = memory[i1]
            if (segment !is FileSegment) {
                var other = memory[i2]
                while (other !is FileSegment) {
                    other = memory[--i2]
                }
                add(other)
                i2--
            } else {
                add(segment)
            }
            i1++
        }
        if (size % 2 != 0) removeLast() // hack
    }

    sealed interface DiskSegment

    data class FileSegment(val id: Int) : DiskSegment {
        override fun toString(): String = "$id"
    }

    data object EmptySegment : DiskSegment {
        override fun toString(): String = "."
    }
}

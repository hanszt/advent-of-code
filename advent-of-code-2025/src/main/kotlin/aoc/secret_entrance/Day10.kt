package aoc.secret_entrance

import aoc.utils.ChallengeDay
import aoc.utils.swap
import java.nio.file.Path
import kotlin.io.path.readLines
import kotlin.time.DurationUnit
import kotlin.time.TimeSource.Monotonic.markNow

/**
 * [aoc.utils.Tag.RECURSIVE]
 *
 * [Day 10 part 2](https://github.com/elizarov/AdventOfCode2025/blob/main/src/Day10_2.kt)
 */
class Day10(private val lines: List<String>) : ChallengeDay {

    constructor(path: Path) : this(path.readLines())

    override fun part1(): Int {
        var ans = 0
        for (s in lines) {
            val goal = s.substringBefore(']').drop(1).mapIndexed { i, c ->
                when (c) {
                    '.' -> 0
                    '#' -> 1
                    else -> error("$c")
                } shl i
            }.sum()
            val wiring = toWiring(s)

            fun find(i: Int, mask: Int): Int {
                if (i >= wiring.size) {
                    return if (mask == goal) 0 else INF
                }
                val best = minOf(
                    find(i + 1, mask),
                    find(i + 1, mask xor wiring[i]) + 1
                )
                return best
            }

            val res = find(0, 0)
            check(res < INF)
            ans += res
        }
        return ans
    }

    override fun part2(): Int = lines.withIndex().toList().parallelStream()
        .mapToInt(::countFewestJoltagePresses)
        .sum()

    private fun countFewestJoltagePresses(iv: IndexedValue<String>): Int {
        val start = markNow()
        val line = iv.value
        val goal = toGoal(line)
        val wiring = toWiring(line)
        reindex(goal, wiring)
        val res = solve(goal, wiring)
        println("${iv.index + 1}: $res in ${start.elapsedNow().toString(DurationUnit.SECONDS, 3)}")
        return res
    }

    fun displayPreparedStatePart2() {
        for ((lineIndex, line) in lines.withIndex()) {
            val goal = toGoal(line)
            val wiring = toWiring(line)
            reindex(goal, wiring)
            println("=== ${lineIndex + 1}:")
            display(wiring, goal)
        }
    }

    private fun display(wiring: IntArray, goal: IntArray) {
        for (i in wiring.indices) {
            val str = goal.indices.joinToString("") { k ->
                when (wiring[i] shr k and 1) {
                    0 -> ".   "
                    1 -> "#   "
                    else -> error("!")
                }
            }
            println(str)
        }
        for (i in goal.indices) {
            print(goal[i].toString().padEnd(4, ' '))
        }
        println()
        println()
    }

    private fun solve(goal: IntArray, wiring: IntArray): Int {
        // solve
        val n = goal.size
        val bc = IntArray(wiring.size + 1)
        for (i in wiring.indices) bc[i] = 32 - wiring[i].countLeadingZeroBits()
        val ff = IntArray(wiring.size + 1)
        for (i in wiring.size - 1 downTo 0) ff[i] = wiring[i] or ff[i + 1]
        val a = IntArray(n)
        fun find(i: Int, j0: Int): Int {
            val bci = bc[i]
            for (k in bci..<j0) if (a[k] < goal[k]) return INF
            val f = ff[i]
            for (k in 0..<bci) if ((f shr k) and 1 == 0 && a[k] < goal[k]) return INF
            if (i >= wiring.size) return 0
            var best = INF
            var cnt = 0
            if (bc[i + 1] < bci) {
                // optimize for the last one
                cnt = goal[bci - 1] - a[bci - 1]
                var ok = true
                for (k in 0..<bci) if ((wiring[i] shr k) and 1 != 0) {
                    a[k] += cnt
                    if (a[k] > goal[k]) ok = false
                }
                if (ok) {
                    best = minOf(best, find(i + 1, bci) + cnt)
                }
            } else {
                // enumerate all the choices
                do {
                    best = minOf(best, find(i + 1, bci) + cnt)
                    cnt++
                    var ok = true
                    for (k in 0..<bci) if ((wiring[i] shr k) and 1 != 0) {
                        a[k]++
                        if (a[k] > goal[k]) ok = false
                    }
                } while (ok)
            }
            for (k in 0..<bc[i]) if ((wiring[i] shr k) and 1 != 0) {
                a[k] -= cnt
            }
            return best
        }

        val res = find(0, n)

        check(res < INF)
        return res
    }

    /**
     * Reindex from highest bit to lowest
     */
    private fun reindex(goal: IntArray, wiring: IntArray) {
        var done = 0
        for (j in goal.lastIndex downTo 0) {
            val wcnt = IntArray(goal.size)
            for (i in done..<wiring.size) {
                val w = wiring[i]
                for (k in 0..j) if (w shr k and 1 != 0) wcnt[k]++
            }
            val j1 = (0..j)
                .sortedBy { goal[it] }
                .minBy { wcnt[it] }
            goal.swap(j , j1)
            for (i in wiring.indices) {
                val w = wiring[i]
                val bj = w shr j and 1
                val bj1 = w shr j1 and 1
                wiring[i] = w and (1 shl j).inv() and (1 shl j1).inv() or (bj shl j1) or (bj1 shl j)
            }
            wiring.sortDescending(done, wiring.size)
            while (done < wiring.size && wiring[done] shr j and 1 != 0) done++
        }
    }

    companion object {

        fun toGoal(line: String): IntArray = line.substringAfter('{')
            .dropLast(1)
            .split(",")
            .map { it.toInt() }
            .toIntArray()

        fun toWiring(line: String): IntArray = line.substringAfter(']')
            .substringBefore('{').trim().split(" ").map { w ->
                w.substring(1, w.length - 1).split(",").sumOf { 1 shl it.toInt() }
            }.toIntArray()

        private const val INF = Int.MAX_VALUE / 2
    }
}

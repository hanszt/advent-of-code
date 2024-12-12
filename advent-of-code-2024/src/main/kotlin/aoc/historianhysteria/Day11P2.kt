package aoc.historianhysteria

import kotlin.collections.groupingBy

/**
 * [Source](https://github.com/elizarov/AdventOfCode2024/blob/main/src/Day11_2.kt)
 *
 * Only refactored to understand it better
 */
fun day11part2Elizarov(a: List<Long>): Long {
    var g0 = a.groupingBy { it }.eachCount().mapValuesTo(HashMap()) { it.value.toLong() }
    var g1 = HashMap(g0)
    fun add(nn: Long, c: Long) {
        g1[nn] = g1.getOrDefault(nn, 0) + c
    }
    repeat(75) {
        g1.clear()
        for ((n, c) in g0) {
            if (n == 0L) {
                add(1, c)
                continue
            }
            var dc = 1
            var pow = 1L
            while (n >= pow * 10) {
                pow *= 10
                dc++
            }
            if (dc % 2 == 0) {
                var pow2 = 1L
                repeat(dc / 2) { pow2 *= 10 }
                val ln = n / pow2
                val rn = n % pow2
                add(ln, c)
                add(rn, c)
            } else {
                val nn = n * 2024
                add(nn, c)
            }
        }
        g1 = g0.also { g0 = g1 }
    }
    return g0.values.sum()
}

package aoc.secret_entrance

import aoc.utils.gcd
import aoc.utils.swap
import kotlin.math.abs
import kotlin.math.sign
import kotlin.time.DurationUnit
import kotlin.time.TimeSource

object Day10ElizarovGauss {
    private const val INF = Int.MAX_VALUE / 2

    fun part2Fast(input: List<String>): Int {
        val start = TimeSource.Monotonic.markNow()
        val ans = input.withIndex().toList().parallelStream()
            .mapToInt(::countFewestJoltagePresses)
            .sum()
        println("Answer: $ans in ${start.elapsedNow().toString(DurationUnit.SECONDS, 3)}")
        return ans
    }

    private fun countFewestJoltagePresses(iv: IndexedValue<String>): Int {
        val start = TimeSource.Monotonic.markNow()
        val res = countFewestJoltagePresses(iv.value)
        println("${iv.index + 1}: $res in ${start.elapsedNow().toString(DurationUnit.SECONDS, 3)}")
        return res
    }

    /**
     * Fast solution for Day 10 Part with Gaussian elimination + brute force of the remaining variables
     *
     * [Gaussian elimination](https://en.wikipedia.org/wiki/Gaussian_elimination)
     */
    private fun countFewestJoltagePresses(s: String): Int {
        val goal = getGoal(s)
        val n = goal.size // constraints
        val w = variables(s, n)
        val m = w.size // variables
        val gMax = goal.max()
        val d = Array(m) { IntArray(m) }
        for (k in 0..<m) d[k][k] = 1
        val bound = IntArray(m)
        val bDiv = IntArray(m) { 1 }
        val r = IntArray(m) { 1 }
        var resDiv = 1
        var free = 0

        var nr = n // n constraints remaining
        var mr = m // m variables remaining
        while (mr > 0 && nr > 0) {
            val ie = nr - 1 // eliminate constraint for goal
            val je = mr - 1 // eliminate variable
            var i1 = -1
            var j1 = -1
            find@ for (i in 0..<nr) for (j in 0..<mr) if (w[j][i] != 0) {
                i1 = i
                j1 = j
                break@find
            }
            if (i1 < 0) {
                // no variables left to eliminate
                // check that all remaining constraints are trivial (0 = 0)
                for (i in 0..<nr) {
                    for (j in 0..<m) check(w[j][i] == 0)
                    check(goal[i] == 0)
                }
                nr = 0
                break
            }
            // swap conditions ie <> i1, variables je <> j1
            d.swap(je, j1)
            r.swap(je, j1)
            w.swap(je, j1)
            for (j in 0..<mr) w[j][ie] = w[j][i1].also { w[j][i1] = w[j][ie] }
            check(w[je][ie] != 0)
            goal[ie] = goal[i1].also { goal[i1] = goal[ie] }
            // eliminate variable je from d * a >= bound
            for (k in 0..<m) {
                if (d[je][k] == 0) continue // is already zero
                val g = abs(d[je][k]).gcd(abs(w[je][ie])) * w[je][ie].sign
                val dMul = w[je][ie] / g
                val eMul = d[je][k] / g
                check(dMul > 0)
                for (j in 0..je) {
                    d[j][k] = dMul * d[j][k] - eMul * w[j][ie]
                }
                bound[k] = dMul * bound[k] - eMul * goal[ie]
                bDiv[k] *= dMul
                check(d[je][k] == 0)
            }
            // eliminate variable je from r * a + free -> min
            if (r[je] != 0) {
                val g = abs(r[je]).gcd(abs(w[je][ie])) * w[je][ie].sign
                val rMul = w[je][ie] / g
                val eMul = r[je] / g
                check(rMul > 0)
                for (j in 0..je) {
                    r[j] = rMul * r[j] - eMul * w[j][ie]
                }
                free = rMul * free + eMul * goal[ie]
                resDiv *= rMul
                check(r[je] == 0)
            }
            // eliminate constraint ie and variable je from w * a == goal
            for (i in 0..ie) {
                if (w[je][i] == 0) continue // is already zero
                val g = abs(w[je][i]).gcd(abs(w[je][ie]))
                val wMul = w[je][ie] / g
                val eMul = w[je][i] / g
                for (j in 0..je) {
                    w[j][i] = wMul * w[j][i] - eMul * w[j][ie]
                }
                goal[i] = wMul * goal[i] - eMul * goal[ie]
            }
            // consistency check: constraint eliminated to trivial 0 = 0
            for (j in 0..<m) check(w[j][ie] == 0)
            check(goal[ie] == 0)
            nr--
            mr--
        }
        check(nr == 0 || mr == 0) // all constraints or all variables should have been eliminated
        // bruteforce remaining mr variables
        // println("${lineIndex + 1}: !!! ${r.slice(0..<mr).joinToString(",")}")
        var res = INF
        val psum = IntArray(m) // partial sum for bounds
        fun bf(j: Int, sum: Int) {
            if (j >= mr) {
                // check bounds
                for (k in 0..<m) if (psum[k] < bound[k] || (psum[k] - bound[k]) % bDiv[k] != 0) return
                if (sum % resDiv != 0) return
                res = minOf(res, sum / resDiv)
                return
            }
            for (aj in 0..gMax) {
                for (k in 0..<m) psum[k] += d[j][k] * aj
                bf(j + 1, sum + r[j] * aj)
                for (k in 0..<m) psum[k] -= d[j][k] * aj
            }
        }
        bf(0, free)
        check(res < INF)
        return res
    }

    private fun getGoal(s: String): IntArray = s.substringAfter('{')
        .dropLast(1)
        .split(",")
        .map { it.toInt() }
        .toIntArray()

    private fun variables(s: String, n: Int): Array<IntArray> {
        return s.substringAfter(']').substringBefore('{').trim()
            .split(" ")
            .map { w ->
                val index = w.substring(1, w.length - 1).split(",").map { it.toInt() }
                val wi = IntArray(n)
                for (j in index) wi[j] = 1
                wi
            }.toTypedArray()
    }
}

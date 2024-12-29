package aoc.historianhysteria

import aoc.utils.ChallengeDay
import java.nio.file.Path
import kotlin.io.path.useLines
import kotlin.math.pow

class Day07(private val equations: List<Equation>) : ChallengeDay {

    constructor(text: String) : this(text.lineSequence().toEquations())
    constructor(path: Path) : this(path.useLines { it.toEquations() })

    /**
     * What is their total calibration result?
     */
    override fun part1(): Long {
        var sum = 0L
        for (eq in equations) {
            val others = eq.values.drop(1)
            val operatorCombinationCount = 2.0.pow(others.size).toInt()

            for (operation in 0..<operatorCombinationCount) {
                var answer = eq.values.first()
                val s = operation.toString(radix = 2)
                val prefix = (s.length..<others.size).joinToString("") { "0" }
                val o = prefix + s
                val combinations = o.map(Char::digitToInt)
                for ((i,other) in others.withIndex()) {
                    when (combinations[i]) {
                        0 -> answer += other
                        1 -> answer *= other
                        else -> error("error")
                    }
                }
                if (answer == eq.answer) {
                    sum += answer
                    break
                }
            }
        }
        return sum
    }

    /**
     * Solution Elizarov
     *
     * What is their total calibration result?
     */
    override fun part2(): Long {
        var sum = 0L
        for ((answer, a) in equations) {
            val p = LongArray(a.size)
            for (i in a.indices) {
                p[i] = 1
                while (p[i] <= a[i]) p[i] = p[i] * 10
            }
            fun scan(cur: Long, i: Int): Boolean {
                if (i >= a.size) {
                    return cur == answer
                }
                if (scan(cur + a[i], i + 1)) return true
                if (scan(cur * a[i], i + 1)) return true
                if (scan(cur * p[i] + a[i], i + 1)) return true
                return false
            }
            if (scan(a[0], 1)) sum += answer
        }
        return sum
    }

    data class Equation(val answer: Long, val values: List<Long>)

    private companion object {
        private fun Sequence<String>.toEquations(): List<Equation> = map(::toEquation).toList()

        private fun toEquation(line: String): Equation = line.splitToSequence(": ", " ")
            .map { it.toLong() }
            .toList()
            .let { Equation(answer = it.first(), values = it.drop(1)) }
    }

}

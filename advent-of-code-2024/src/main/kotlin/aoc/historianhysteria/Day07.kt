package aoc.historianhysteria

import aoc.utils.ChallengeDay
import java.nio.file.Path
import kotlin.io.path.useLines
import kotlin.math.pow

class Day07(private val equations: List<Equation>) : ChallengeDay {

    constructor(text: String) : this(text.lineSequence().toEquations())
    constructor(path: Path) : this(path.useLines { it.toEquations() })

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
                val combinations = o.map { i -> i.digitToInt() }
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

    override fun part2(): Int {
        TODO()
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

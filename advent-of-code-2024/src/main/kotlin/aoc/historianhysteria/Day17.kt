package aoc.historianhysteria

import aoc.utils.ChallengeDay
import aoc.utils.parts
import java.nio.file.Path
import kotlin.io.path.readLines

class Day17(private val input: List<String>) : ChallengeDay {
    constructor(path: Path) : this(path.readLines())

    /**
     * what do you get if you use commas to join the values it output into a single string?
     */
    override fun part1(): String = part1Elizarov()

    /**
     * What is the lowest positive initial value for register A that causes the program to output a copy of itself?
     */
    override fun part2(): Long = Day17Zebalu(input).part2()

    fun part1Elizarov(): String {
        val (regs, prog) = input.parts { it }
        val r = regs.map { s ->
            Regex("Register [A-C]: (\\d+)").matchEntire(s)?.groupValues[1]?.toInt() ?: error("Register not found")
        }.toIntArray()
        val p = prog[0].substringAfter(": ").split(",").map { it.toInt() }.toIntArray()
        var ip = 0
        fun combo(): Int {
            val v = p[ip + 1]
            return when (v) {
                in 0..3 -> v
                in 4..6 -> r[v - 4]
                else -> error("v=$v")
            }
        }

        val out = ArrayList<Int>()
        fun div2(a: Int, b: Int): Int {
            if (b > 31) return 0
            return a shr b
        }
        while (ip < p.size) {
            when (p[ip]) {
                0 -> r[0] = div2(r[0], combo())
                1 -> r[1] = r[1] xor p[ip + 1]
                2 -> r[1] = combo() and 7
                3 -> if (r[0] != 0) {
                    ip = p[ip + 1]; continue
                }

                4 -> r[1] = r[1] xor r[2]
                5 -> out += combo() and 7
                6 -> r[1] = div2(r[0], combo())
                7 -> r[2] = div2(r[0], combo())
            }
            ip += 2
        }
        return out.joinToString(",")
    }
}

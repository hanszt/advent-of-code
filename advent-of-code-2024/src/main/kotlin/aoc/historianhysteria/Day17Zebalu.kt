package aoc.historianhysteria

import java.util.*
import kotlin.math.min
import kotlin.math.pow

/**
 * Thanks to Zebalu
 */
class Day17Zebalu(lines: List<String>) {
    private val regA = lines[0].split("Register A: ")[1].toLong()
    private val regB = lines[1].split("Register B: ")[1].toLong()
    private val regC = lines[2].split("Register C: ")[1].toLong()
    private val instr = lines[4].split("Program: ")[1].split(",").map(String::toInt)

    fun part1(): String = Computer(regA.toLong(), regB, regC, instr).run {
        execute()
        print()
    }

    fun part2(): Long {
        val queue = PriorityQueue<State>(compareBy(State::registerA))
        queue.add(State(0, 0))
        var min = Long.Companion.MAX_VALUE
        while (!queue.isEmpty() && min == Long.MAX_VALUE) {
            val state = queue.poll()
            val suffix = instr.subList(instr.size - state.outputSize - 1, instr.size)
            val nextRegisterABase = state.registerA shl 3
            for (i in 0L..8L - 1) {
                val nextRegisterA = nextRegisterABase or i
                val computer = Computer(nextRegisterA, regB, regC, instr)
                computer.execute()
                if (computer.outPutMatches(suffix)) {
                    if (instr.size == suffix.size) {
                        min = min(min.toDouble(), nextRegisterA.toDouble()).toLong()
                    }
                    queue.add(State(nextRegisterA, state.outputSize + 1))
                }
            }
        }
        return min
    }

    private class Computer(
        private var a: Long,
        private var b: Long,
        private var c: Long,
        private val opcodes: List<Int>
    ) {
        private val out = ArrayList<Int>()
        private var ip = 0

        fun execute() {
            while (ip < opcodes.size) {
                val litOp = opcodes[ip + 1]
                val combOp = getCombo(opcodes[ip + 1])
                var skipIncrease = false
                when (opcodes[ip]) {
                    0 -> {
                        val den = 2.0.pow(combOp.toDouble()).toLong()
                        a = a / den
                    }

                    1 -> b = b xor (litOp.toLong())
                    2 -> b = combOp % 8
                    3 -> {
                        if (a != 0L) {
                            ip = litOp
                            skipIncrease = true
                        }
                    }

                    4 -> b = b xor c
                    5 -> out.add((combOp % 8L).toInt())
                    6 -> {
                        val den = 2.0.pow(combOp.toDouble()).toLong()
                        b = a / den
                    }

                    7 -> {
                        val den = 2.0.pow(combOp.toDouble()).toLong()
                        c = a / den
                    }
                }
                if (!skipIncrease) {
                    ip += 2
                }
            }
        }

        fun outPutMatches(expected: List<Int>): Boolean = expected == out

        fun getCombo(value: Int): Long {
            return when (value % 8) {
                0, 1, 2, 3 -> value.toLong()
                4 -> a
                5 -> b
                6 -> c
                7 -> Long.MIN_VALUE
                else -> error("Impossible to reach")
            }
        }

        fun print(): String = out.joinToString(",")
    }

    @JvmRecord
    private data class State(val registerA: Long, val outputSize: Int)
}

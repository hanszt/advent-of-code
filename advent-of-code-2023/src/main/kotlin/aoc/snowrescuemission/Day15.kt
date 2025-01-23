package aoc.snowrescuemission

import aoc.utils.ChallengeDay
import java.nio.file.Path
import kotlin.io.path.readText

class Day15(private val initSequence: String) : ChallengeDay {
    constructor(path: Path) : this(path.readText())

    /**
     * Run the HASH algorithm on each step in the initialization sequence.
     * What is the sum of the results? (The initialization sequence is one long line; be careful when copy-pasting it.)
     *
     * https://github.com/elizarov/AdventOfCode2023/blob/main/src/Day15_1.kt
     */
    override fun part1(): Int = initSequence.split(",").sumOf(::hash)

    /**
     * With the help of an over-enthusiastic reindeer in a hard hat, follow the initialization sequence.
     * What is the focusing power of the resulting lens configuration?
     *
     * https://github.com/elizarov/AdventOfCode2023/blob/main/src/Day15_2.kt
     */
    override fun part2(): Long {
        data class Lens(val boxLabel: String, val focalLength: Int)

        val nrOfBoxes = 256
        val lensBoxes = Array(nrOfBoxes) { mutableListOf<Lens>() }
        for (instruction in initSequence.split(",")) {
            val op = if (instruction.contains('-')) '-' else '='
            val (boxLabel, focalLength) = instruction.split(op)
            val hash = hash(boxLabel)
            when (op) {
                '-' -> {
                    val i = lensBoxes[hash].indexOfFirst { it.boxLabel == boxLabel }
                    if (i >= 0) lensBoxes[hash].removeAt(i)
                }

                '=' -> {
                    val foc = focalLength.toInt()
                    val i = lensBoxes[hash].indexOfFirst { it.boxLabel == boxLabel }
                    if (i < 0) {
                        lensBoxes[hash].add(Lens(boxLabel, foc))
                    } else {
                        lensBoxes[hash].removeAt(i)
                        lensBoxes[hash].add(i, Lens(boxLabel, foc))
                    }
                }
            }
        }
        return lensBoxes.withIndex().sumOf { (i, lenses) ->
            lenses.withIndex().sumOf { (j, lens) ->
                (i + 1) * (j + 1).toLong() * lens.focalLength
            }
        }
    }

    private fun hash(s: String): Int {
        var h = 0
        for (c in s) {
            h = ((h + c.code) * 17) and 0xff // 'x and 0xff' equivalent to 'x % 256'
        }
        return h
    }
}

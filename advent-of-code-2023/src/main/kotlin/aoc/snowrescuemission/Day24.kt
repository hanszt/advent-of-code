package aoc.snowrescuemission

import aoc.utils.ChallengeDay
import java.nio.file.Path
import kotlin.io.path.readLines

/**
 * [aoc.utils.Tag.THREE_D]
 */
class Day24(private val input: List<String>, min: Long, max: Long) : ChallengeDay {
    constructor(path: Path) : this(input = path.readLines(), min = 200000000000000L, max = 400000000000000L)

    private val day24Abnew = Day24Abnew(input, min, max)

    /**
     * How many of these intersections occur within the test area?
     */
    override fun part1(): Long = day24Abnew.part1()

    /**
     * Determine the exact position and velocity the rock needs to have at time 0 so that it perfectly collides with every hailstone.
     * What do you get if you add up the X, Y, and Z coordinates of that initial position?
     */
    override fun part2(): Long = part2Elizarov()

    fun part2Elizarov(): Long {
        val a = input.map { s ->
            s.split("@").map { vs -> vs.split(",").map { it.trim().toLong() } }
        }
        val tm = (0..2).firstNotNullOf { k ->
            a.indices.firstNotNullOfOrNull { i ->
                a.indices.map { j ->
                    if (a[j][1][k] == a[i][1][k]) (if (a[j][0][k] == a[i][0][k]) 0L else -1L) else {
                        val tn = a[j][0][k] - a[i][0][k]
                        val td = a[i][1][k] - a[j][1][k]
                        if (tn % td == 0L) tn / td else -1L
                    }
                }.takeIf { tm -> tm.all { it >= 0 } }
            }
        }
        val (i, j) = tm.withIndex().filter { it.value > 0 }.map { it.index }
        fun p(i: Int, k: Int, t: Long) = a[i][0][k] + a[i][1][k] * t
        return (0..2).sumOf { k -> p(i, k, tm[i]) - (p(i, k, tm[i]) - p(j, k, tm[j])) / (tm[i] - tm[j]) * tm[i] }
    }
}

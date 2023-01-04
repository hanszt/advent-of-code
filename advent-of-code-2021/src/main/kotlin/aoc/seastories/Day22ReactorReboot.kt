package aoc.seastories

import aoc.utils.self
import java.io.File

internal object Day22ReactorReboot : ChallengeDay {

    fun part1(path: String): Int {
        val range = -50..50
        val rangeSize = range.toList().size

        val targetRegion = Array(rangeSize) { Array(rangeSize) { BooleanArray(rangeSize) } }

        File(path).readLines()
            .map(::toCuboid)
            .forEach { targetRegion.update(range, it) }
        return targetRegion.sumOf { it.sumOf { array -> array.count(::self) } }
    }

    private fun Array<Array<BooleanArray>>.update(targetRange: IntRange, cuboid: Cuboid) {
        for (x in targetRange) {
            for (y in targetRange) {
                for (z in targetRange) {
                    if (x in cuboid.xRange && y in cuboid.yRange && z in cuboid.zRange) {
                        this[z - targetRange.first][y - targetRange.first][x - targetRange.first] = cuboid.on
                    }
                }
            }
        }
    }

    // credits to Roman Elizarov
    fun part2(path: String): Long {
        val instructions = File(path).readLines().map(::toCuboid)

        val ux = instructions.map(Cuboid::xRange).flatMap { listOf(it.first, it.last + 1) }.distinct().sorted()
        val uy = instructions.map(Cuboid::yRange).flatMap { listOf(it.first, it.last + 1) }.distinct().sorted()
        val uz = instructions.map(Cuboid::zRange).flatMap { listOf(it.first, it.last + 1) }.distinct().sorted()

        val matrixX = ux.withIndex().associateBy({ it.value }, { it.index })
        val matrixY = uy.withIndex().associateBy({ it.value }, { it.index })
        val matrixZ = uz.withIndex().associateBy({ it.value }, { it.index })

        val reactorOn = Array(ux.size) { Array(uy.size) { BooleanArray(uz.size) } }
        for ((on, xRange, yRange, zRange) in instructions) {
            for (x in matrixX[xRange.first]!! until matrixX[xRange.last + 1]!!) {
                for (y in matrixY[yRange.first]!! until matrixY[yRange.last + 1]!!) {
                    for (z in matrixZ[zRange.first]!! until matrixZ[zRange.last + 1]!!) {
                        reactorOn[x][y][z] = on
                    }
                }
            }
        }
        return reactorOn.calculateNrOfCubesOn(ux, uy, uz)
    }

    private fun Array<Array<BooleanArray>>.calculateNrOfCubesOn(ux: List<Int>, uy: List<Int>, uz: List<Int>): Long {
        var nrOfCubesOn = 0L
        for (x in 0 until ux.lastIndex) {
            for (y in 0 until uy.lastIndex) {
                for (z in 0 until uz.lastIndex) {
                    val isOn = this[x][y][z]
                    if (isOn) {
                        nrOfCubesOn += (ux[x + 1] - ux[x]).toLong() * (uy[y + 1] - uy[y]).toLong() * (uz[z + 1] - uz[z]).toLong()
                    }
                }
            }
        }
        return nrOfCubesOn
    }

    private fun toCuboid(s: String): Cuboid = s.split(' ').let { (instr, ranges) ->
        ranges.split(',').map(::toIntRange)
            .let { (xRange, yRange, zRange) -> Cuboid(instr == "on", xRange, yRange, zRange) }
    }

    private fun toIntRange(xRange: String) = xRange.substring(2).split("..")
        .map(String::toInt).let { (start, end) -> start..end }

    override fun part1() = part1(ChallengeDay.inputDir + "/day22.txt")
    override fun part2() = part2(ChallengeDay.inputDir + "/day22.txt")

    data class Cuboid(val on: Boolean, val xRange: IntRange, val yRange: IntRange, val zRange: IntRange)
}

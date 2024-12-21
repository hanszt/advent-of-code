package aoc.seastories

import aoc.utils.invoke
import java.io.File

internal class Day22ReactorReboot(private val inputPath: String) : ChallengeDay {

    override fun part1(): Int {
        val range = -50..50
        val rangeSize = range.toList().size

        val targetRegion = Array(rangeSize) { Array(rangeSize) { BooleanArray(rangeSize) } }

        File(inputPath).readLines()
            .map(::toCuboid)
            .forEach { targetRegion.update(range, it) }
        return targetRegion.sumOf { it.sumOf { it.count { it } } }
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
    override fun part2(): Long {
        val cuboids = File(inputPath).readLines().map(::toCuboid)

        val ux = cuboids.map(Cuboid::xRange).flatMap { listOf(it.first, it.last + 1) }.distinct().sorted()
        val uy = cuboids.map(Cuboid::yRange).flatMap { listOf(it.first, it.last + 1) }.distinct().sorted()
        val uz = cuboids.map(Cuboid::zRange).flatMap { listOf(it.first, it.last + 1) }.distinct().sorted()

        val matrixX = ux.withIndex().associate { (i, x) -> x to i }
        val matrixY = uy.withIndex().associate { (i, y) -> y to i }
        val matrixZ = uz.withIndex().associate { (i, z) -> z to i }

        val reactorMatrix = Array(ux.size) { Array(uy.size) { BooleanArray(uz.size) } }

        for ((on, xRange, yRange, zRange) in cuboids) {
            for (x in matrixX(xRange.first)..<matrixX(xRange.last + 1)) {
                for (y in matrixY(yRange.first)..<matrixY(yRange.last + 1)) {
                    for (z in matrixZ(zRange.first)..<matrixZ(zRange.last + 1)) {
                        reactorMatrix[x][y][z] = on
                    }
                }
            }
        }
        return reactorMatrix.nrOfCubesOn(ux, uy, uz)
    }

    private fun Array<Array<BooleanArray>>.nrOfCubesOn(ux: List<Long>, uy: List<Long>, uz: List<Long>): Long {
        var nrOfCubesOn = 0L
        for (x in 0..<ux.lastIndex) {
            for (y in 0..<uy.lastIndex) {
                for (z in 0..<uz.lastIndex) {
                    if (this[x][y][z]) {
                        nrOfCubesOn += (ux[x + 1] - ux[x]) * (uy[y + 1] - uy[y]) * (uz[z + 1] - uz[z])
                    }
                }
            }
        }
        return nrOfCubesOn
    }

    private fun toCuboid(s: String): Cuboid = s.split(' ').let { (instr, ranges) ->
        ranges.split(',').map(::toLongRange)
            .let { (xRange, yRange, zRange) -> Cuboid(instr == "on", xRange, yRange, zRange) }
    }

    private fun toLongRange(xRange: String) = xRange.substring(2).split("..")
        .map(String::toLong).let { (start, end) -> start..end }

    data class Cuboid(val on: Boolean, val xRange: LongRange, val yRange: LongRange, val zRange: LongRange)
}

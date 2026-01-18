package aoc.seastories

import aoc.utils.distinctUntilChanged
import aoc.utils.invoke
import java.io.File
import java.util.*

internal class Day22ReactorReboot(private val inputPath: String) : ChallengeDay {

    override fun part1(): Int {
        val range = -50..50
        val rangeSize = range.toList().size

        val targetRegion = Array(rangeSize) { Array(rangeSize) { BooleanArray(rangeSize) } }

        File(inputPath).readLines()
            .map(::toCuboid)
            .forEach { targetRegion.update(range, it) }
        return targetRegion.sumOf { blocks -> blocks.sumOf { rows -> rows.count { it } } }
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
        val cuboids = File(inputPath).useLines {
            it.map(::toCuboid).toList()
        }
        val ux = cuboids.toCoordsBy(Cuboid::xRange)
        val uy = cuboids.toCoordsBy(Cuboid::yRange)
        val uz = cuboids.toCoordsBy(Cuboid::zRange)

        val matrixX = ux.withIndex().associate { (i, x) -> x to i }
        val matrixY = uy.withIndex().associate { (i, y) -> y to i }
        val matrixZ = uz.withIndex().associate { (i, z) -> z to i }

        val sizeX = ux.size
        val sizeY = uy.size
        val sizeZ = uz.size
        val reactorMatrix = BitSet(sizeX * sizeY * sizeZ)

        for ((on, xRange, yRange, zRange) in cuboids) {
            val xStart = matrixX(xRange.first)
            val xEnd = matrixX(xRange.last + 1)

            val yStart = matrixY(yRange.first)
            val yEnd = matrixY(yRange.last + 1)

            val zStart = matrixZ(zRange.first)
            val zEnd = matrixZ(zRange.last + 1)

            for (x in xStart..<xEnd) {
                val xOffset = x * sizeY * sizeZ
                for (y in yStart..<yEnd) {
                    val yOffset = y * sizeZ
                    // Calculate the 1D range for the entire Z-row
                    val fromIndex = xOffset + yOffset + zStart
                    val toIndex = xOffset + yOffset + zEnd
                    if (on) {
                        reactorMatrix.set(fromIndex, toIndex)
                    } else {
                        reactorMatrix.clear(fromIndex, toIndex)
                    }
                }
            }
        }
        return reactorMatrix.calculateVolume(ux, uy, uz)
    }

    /**
     * Pro-Tip: Memory Efficiency During Volume Calculation
     * If you find that the triple-loop for volume calculation is slow, you can use the BitSet.nextSetBit(index) method.
     * This allows you to skip all the "off" (false) bits entirely, which is much faster if your reactor is mostly empty.
     */
    private fun BitSet.calculateVolume(ux: List<Long>, uy: List<Long>, uz: List<Long>): Long {
        val sizeX = ux.size
        val sizeY = uy.size
        val sizeZ = uz.size
        var totalPhysicalVolume: Long = 0
        var i = nextSetBit(0)
        while (i >= 0 && i < (sizeX * sizeY * sizeZ)) {
            // Convert 1D index back to 3D
            val x = i / (sizeY * sizeZ)
            val y = (i / sizeZ) % sizeY
            val z = i % sizeZ

            // Calculate volume for this specific cell
            val vol = (ux[x + 1] - ux[x]) * (uy[y + 1] - uy[y]) * (uz[z + 1] - uz[z])
            totalPhysicalVolume += vol

            i = nextSetBit(i + 1)
        }
        return totalPhysicalVolume
    }

    private fun List<Cuboid>.toCoordsBy(selector: (Cuboid) -> LongRange): List<Long> = asSequence()
        .map(selector)
        .flatMap { listOf(it.first, it.last + 1) }
        .sorted()
        .distinctUntilChanged()
        .toList()

    private fun toCuboid(s: String): Cuboid = s.split(' ').let { (instr, ranges) ->
        ranges.split(',').map(::toLongRange)
            .let { (xRange, yRange, zRange) -> Cuboid(instr == "on", xRange, yRange, zRange) }
    }

    private fun toLongRange(xRange: String) = xRange.substring(2).split("..")
        .map(String::toLong).let { (start, end) -> start..end }

    data class Cuboid(val on: Boolean, val xRange: LongRange, val yRange: LongRange, val zRange: LongRange)
}

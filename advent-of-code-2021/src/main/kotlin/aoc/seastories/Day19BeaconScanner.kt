package aoc.seastories

import aoc.utils.grid3d.Transform3D
import aoc.utils.grid3d.GridPoint3D
import aoc.utils.grid3d.gridPoint3D
import aoc.utils.grid3d.rotations
import aoc.utils.splitByBlankLine
import aoc.utils.toSetOf
import aoc.utils.grid3d.transform
import java.io.File
import kotlin.math.max

typealias ScannerData = List<Set<GridPoint3D>>

private const val OVERLAPPING_BEACON_COUNT = 12

/**
 * I've not been able to solve this day myself. This solution is from the repo from Elizarov. All credits go to him.
 *
 * I did a little refactoring and renaming to understand what is going on.
 *
 * It is very educational to see how such a solution can be solved. Many thanks to Roman Elizarov
 *
 * @see <a href="https://github.com/elizarov/AdventOfCode2021/blob/main/src/Day19.kt">Day19 elizarov solution</a>
 */
internal class Day19BeaconScanner(inputPath: String) : ChallengeDay {

    private val beaconCount: Int
    private val scannerPositions: Array<GridPoint3D>

    init {
        val (beaconCount, scannerPositions) = calculateBeaconCountAndScannerPositions(inputPath)
        this.beaconCount = beaconCount
        this.scannerPositions = scannerPositions
    }

    override fun part1(): Int = beaconCount
    override fun part2(): Int = scannerPositions.largestDistance()

    private fun String.toScannerDataSets(): ScannerData =
        splitByBlankLine().map { s -> s.lines().filter { "scanner" !in it }.toSetOf(::toPoint3D) }

    private fun toPoint3D(p: String) = p.split(",").map(String::toInt).let { (x, y, z) -> gridPoint3D(x, y, z) }

    private fun Array<GridPoint3D>.largestDistance(): Int {
        var max = 0
        for (i in indices) {
            for (j in i + 1..<size) {
                max = max(max, this[i].manhattanDistance(this[j]))
            }
        }
        return max
    }

    private fun ScannerData.findMatch(scannerIndex: Int, otherScannerIndex: Int): Transform3D? {
        val detectedPoints = this[scannerIndex]
        for (rotation in rotations) {
            val rotatedPointsOther = this[otherScannerIndex].map(rotation)
            val translation = detectedPoints.asSequence()
                .flatMap { detected -> rotatedPointsOther.map { detected - it } }
                .groupingBy { it }
                .eachCount()
                .filterValues { it >= OVERLAPPING_BEACON_COUNT }
                .keys.singleOrNull() ?: continue
            return Transform3D(rotation, translation)
        }
        return null
    }

    private fun calculateBeaconCountAndScannerPositions(path: String): Pair<Int, Array<GridPoint3D>> {
        val scannerDataSets = File(path).readText().toScannerDataSets()

        val beacons = scannerDataSets.first().toMutableSet()

        val scannerPositions = Array(scannerDataSets.size) { GridPoint3D.ZERO }

        val found = ArrayDeque<Int>().apply { add(0) }
        val remaining = (1 until scannerDataSets.size).toMutableSet()

        val transform3Ds = Array(scannerDataSets.size) { emptyList<Transform3D>() }
        while (remaining.isNotEmpty()) {
            val firstFound = found.removeFirst()
            for (remainingScanner in remaining.toList()) {
                val transformMatch = scannerDataSets.findMatch(firstFound, remainingScanner) ?: continue
                val transforms = listOf(transformMatch) + transform3Ds[firstFound]
                transform3Ds[remainingScanner] = transforms
                scannerPositions[remainingScanner] = GridPoint3D.ZERO.transform(transforms)
                beacons.addAll(scannerDataSets[remainingScanner].map { it.transform(transforms) })
                found.add(remainingScanner)
                remaining.remove(remainingScanner)
            }
        }
        return beacons.size to scannerPositions
    }
}

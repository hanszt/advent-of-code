package aoc.seastories

import aoc.utils.Transform3D
import aoc.utils.grouping
import aoc.utils.model.GridPoint3D
import aoc.utils.model.gridPoint3D
import aoc.utils.orientations
import aoc.utils.rotated
import aoc.utils.splitByBlankLine
import aoc.utils.transform
import java.io.File

typealias ScannerData = List<Set<GridPoint3D>>

/**
 * I've not been able to solve this day myself. This solution is from the repo from Elizarov. All credits go to him.
 *
 * I did a little refactoring and renaming to understand what is going on.
 *
 * It is very educational to see how such a solution can be solved. Many thanks to Roman Elizarov
 *
 * @see <a href="https://github.com/elizarov/AdventOfCode2021/blob/main/src/Day19.kt">Day19 elizarov solution</a>
 */
internal object Day19BeaconScanner : ChallengeDay {

    private fun String.toScannerDataSets(): ScannerData =
        splitByBlankLine().map { s -> s.lines().filter { "scanner" !in it }.map(::toPoint3D).toSet() }

    private fun toPoint3D(p: String) = p.split(",").map(String::toInt).let { (x, y, z) -> gridPoint3D(x, y, z) }

    private fun Array<GridPoint3D>.largestDistance(): Int = flatMap { map(it::manhattanDistance) }.max()

    private fun ScannerData.findMatch(scannerIndex: Int, otherScannerIndex: Int): Transform3D? {
        val detectedPoints = this[scannerIndex]
        for (orientation in orientations.indices) {
            val rotatedPointsOther = this[otherScannerIndex].map { it.rotated(orientation) }
            val translation = detectedPoints.asSequence()
                .flatMap { detected -> rotatedPointsOther.map { detected - it } }
                .grouping
                .eachCount()
                .filterValues { it >= 12 }
                .keys.firstOrNull() ?: continue
            return Transform3D(orientation, translation)
        }
        return null
    }

    @Suppress("kotlin:S1481")
    private fun calculateBeaconCountAndScannerPositions(path: String): Pair<Int, Array<GridPoint3D>> {
        val scannerDataSets = File(path).readText().toScannerDataSets()

        val beacons = scannerDataSets.first().toMutableSet()

        val scannerPositions = Array(scannerDataSets.size) { GridPoint3D.ZERO }

        val found = ArrayDeque<Int>().apply { add(0) }
        val remaining = (1 until scannerDataSets.size).toMutableSet()

        val transform3Ds = Array<List<Transform3D>>(scannerDataSets.size) { emptyList() }
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

    fun part1(path: String): Int = calculateBeaconCountAndScannerPositions(path).first
    fun part2(path: String): Int = calculateBeaconCountAndScannerPositions(path).second.largestDistance()

    override fun part1() = part1(ChallengeDay.inputDir + "/day19.txt")
    override fun part2() = part2(ChallengeDay.inputDir + "/day19.txt")
}

package aoc.seastories

import aoc.utils.*
import aoc.utils.model.GridPoint3D
import java.io.File
import aoc.utils.model.GridPoint3D.Companion.by
import aoc.utils.model.GridPoint2D.Companion.by
import kotlin.collections.max

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

    private fun String.toScannerDataSets(): List<Set<GridPoint3D>> =
        splitByBlankLine().map { s -> s.lines().filterNot { "scanner" in it }.map(::toPoint3D).toSet() }

    private fun toPoint3D(p: String) = p.split(",").map(String::toInt).let { (x, y, z) -> x by y by z }

    private fun Array<GridPoint3D>.largestDistance(): Int = flatMap { point -> map(point::manhattanDistance) }.max()

    private fun List<Set<GridPoint3D>>.findMatch(scannerIndex: Int, otherScannerIndex: Int): Transform3D? {
        val detectedPoints = this[scannerIndex]
        for (orientation in orientations.indices) {
            val rotatedPointsOther = this[otherScannerIndex].map { it.rotate(orientation) }
            val translation = sequence { compare(detectedPoints, rotatedPointsOther) }
                .groupingBy(::self)
                .eachCount()
                .filterValues { it >= 12 }
                .keys.firstOrNull() ?: continue
            return Transform3D(orientation, translation)
        }
        return null
    }

    private suspend fun SequenceScope<GridPoint3D>.compare(points: Iterable<GridPoint3D>, rotatedOther: Iterable<GridPoint3D>) {
        for (detected in points) {
            for (rotated in rotatedOther) {
                yield(detected - rotated)
            }
        }
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

package aoc.seastories

import aoc.utils.*
import aoc.utils.grid2d.*
import java.io.File

internal class Day11DumboOctopus(private val inputPath: String) : ChallengeDay {

    override fun part1(): Int = File(inputPath).readLines()
        .toGrid { Octopus(it.digitToInt()) }
        .simulateEnergyLevels(steps = 100)
        .flatten()
        .sumOf(Octopus::nrFlashes)

    override fun part2(): Int = File(inputPath).readLines()
        .toGrid { Octopus(it.digitToInt()) }
        .findSynchronizationStep()

    private fun Grid<Octopus>.findSynchronizationStep(): Int = generateSequence { simulateStep() }
        .takeWhile { anyInGrid { it.energyLevel != 1 } }
        .count()

    private fun Grid<Octopus>.simulateEnergyLevels(steps: Int): Grid<Octopus> =
        apply { (1..steps).map { simulateStep() } }

    companion object {
        internal fun Grid<Octopus>.simulateStep() {
            asSequence().flatten().forEach(Octopus::incrementEnergy)
            updateEnergyLevelsNeighbors()
        }

        private fun Grid<Octopus>.updateEnergyLevelsNeighbors() {
            val differences = Array(height) { IntArray(width) }
            val gridPoints = gridPoints().toList()
            gridPoints.forEach { updateDifferencesNeighbors(it, differences) }
            if (differences.allInGrid { it == 0 }) return
            gridPoints.forEach { this[it].updateEnergyLevel(differences[it]) }
            updateEnergyLevelsNeighbors()
        }

        private fun Grid<Octopus>.updateDifferencesNeighbors(p: GridPoint2D, differences: Array<IntArray>) {
            val octopus = this[p]
            if (octopus.isFlashing()) {
                octopus.incrementFlashes()
                GridPoint2D.kingDirs.map { d -> p + d }
                    .forEach { p -> getOrNull(p)?.run { differences[p]++ } }
            }
        }
    }

    internal data class Octopus(var energyLevel: Int) {

        var nrFlashes = 0
            private set
        private var flashed = false

        fun updateEnergyLevel(difference: Int) {
            energyLevel += difference
            if (flashed) energyLevel = 0
        }

        fun isFlashing(): Boolean = energyLevel > 9 && flashed.not()

        fun incrementFlashes() = (nrFlashes++).also { flashed = true }

        fun incrementEnergy() = (energyLevel++).also { flashed = false }

        override fun toString(): String = "$energyLevel $flashed $nrFlashes"
    }
}

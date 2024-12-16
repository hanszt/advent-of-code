package aoc.seastories

import aoc.utils.*
import aoc.utils.model.GridPoint2D
import aoc.utils.model.GridPoint2D.Companion.by
import java.io.File

internal class Day11DumboOctopus(private val inputPath: String) : ChallengeDay {

    override fun part1(): Int = File(inputPath).readLines()
        .toGridOf(Char::digitToInt)
        .toGridOf(::Octopus)
        .simulateEnergyLevels(steps = 100)
        .flatMap(Array<Octopus>::asList)
        .sumOf(Octopus::nrFlashes)

    override fun part2(): Int = File(inputPath).readLines()
        .toGridOf { Octopus(it.digitToInt()) }
        .findSynchronizationStep()

    private fun Grid<Octopus>.findSynchronizationStep(): Int = generateSequence { simulateStep() }
        .takeWhile { anyInGrid { it.energyLevel != 1 } }
        .count()

    private fun Grid<Octopus>.simulateEnergyLevels(steps: Int): Grid<Octopus> =
        apply { (1..steps).map { simulateStep() } }

    companion object {
        internal fun Grid<Octopus>.simulateStep() {
            forEachInGrid(Octopus::incrementEnergy)
            updateEnergyLevelsNeighbors()
        }

        private fun Grid<Octopus>.updateEnergyLevelsNeighbors() {
            val differences = Array(size) { IntArray(first().size) }
            forEachPoint { x, y -> updateDifferencesNeighbors(x, y, differences) }
            if (differences.allInGrid { it == 0 }) return
            forEachPointAndValue { x, y, octopus -> octopus.updateEnergyLevel(differences[y][x]) }
            updateEnergyLevelsNeighbors()
        }

        private fun Grid<Octopus>.updateDifferencesNeighbors(x: Int, y: Int, differences: IntGrid) {
            val octopus = this[y][x]
            if (octopus.isFlashing()) {
                octopus.incrementFlashes()
                GridPoint2D.kingDirs.map { (dx, dy) -> x + dx by y + dy }
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

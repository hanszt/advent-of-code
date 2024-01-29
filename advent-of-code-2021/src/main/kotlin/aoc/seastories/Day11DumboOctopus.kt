package aoc.seastories

import aoc.utils.*
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

    private fun Array<Array<Octopus>>.findSynchronizationStep(): Int = generateSequence { simulateStep() }
        .takeWhile { anyInGrid { it.energyLevel != 1 } }
        .count()

    private fun Array<Array<Octopus>>.simulateEnergyLevels(steps: Int): Array<Array<Octopus>> =
        apply { (1..steps).map { simulateStep() } }

    companion object {
        internal fun Array<Array<Octopus>>.simulateStep() {
            forEachInGrid(Octopus::incrementEnergy)
            updateEnergyLevelsNeighbors()
        }

        private fun Array<Array<Octopus>>.updateEnergyLevelsNeighbors() {
            val differences = Array(size) { IntArray(first().size) }
            forEachPoint { x, y -> updateDifferencesNeighbors(x, y, differences) }
            if (differences.allInGrid { it == 0 }) return
            forEachPointAndValue { x, y, octopus -> octopus.updateEnergyLevel(differences[y][x]) }
            updateEnergyLevelsNeighbors()
        }

        private val dirs = listOf(0 to 1, 1 to 1, 1 to 0, 1 to -1, 0 to -1, -1 to -1, -1 to 0, -1 to 1)

        private fun Array<Array<Octopus>>.updateDifferencesNeighbors(x: Int, y: Int, differences: Array<IntArray>) {
            val octopus = this[y][x]
            if (octopus.isFlashing()) {
                octopus.incrementFlashes()
                dirs.map { (dx, dy) -> x + dx to y + dy }
                    .forEach { (nx, ny) -> getOrNull(ny)?.getOrNull(nx)?.run { differences[ny][nx]++ } }
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

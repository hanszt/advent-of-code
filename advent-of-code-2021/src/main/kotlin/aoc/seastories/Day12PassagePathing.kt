package aoc.seastories

import aoc.utils.graph.MutableNode
import aoc.utils.graph.allPathsByDfs
import aoc.utils.graph.toBiDiGraph
import aoc.utils.linkedListOf
import java.io.File

internal class Day12PassagePathing(inputPath: String) : ChallengeDay {

    private val lines = File(inputPath).readLines()

    /**
     * How many paths through this cave system are there that visit small caves at most once?
     */
    override fun part1(): Int = lines.toBiDiGraph('-').countDistinctPaths()

    /**
     * How many paths through this cave system are there?
     */
    override fun part2(): Int {
        val graph = lines
            .toBiDiGraph("-") { label -> Cave(label, allowedToVisitTwice = false) }
        val start = graph["start"] ?: error("start not found")
        val end = graph["end"] ?: error("end not found")

        return graph.filter { (label) -> label != "start" && label != "end" && label.all(Char::isLowerCase) }
            .flatMap { (_, allowedToVisitTwice) -> findPathsByBfs(start, end, allowedToVisitTwice) }
            .distinct()
            .count()
    }

    private fun Map<String, MutableNode<String>>.countDistinctPaths(): Int = let { graph ->
        val start = graph["start"] ?: error("start not found")
        val end = graph["end"] ?: error("end not found")
        return allPathsByDfs(start, end) { it.value.all(Char::isLowerCase) }.count()
    }

    private fun findPathsByBfs(src: MutableNode<Cave>, goal: MutableNode<Cave>, caveAllowedToVisitTwice: MutableNode<Cave>): Set<String> {
        val uniquePaths = mutableSetOf<String>()

        val pathsQueue = linkedListOf(listOf(src))
        while (pathsQueue.isNotEmpty()) {
            val currentPath = pathsQueue.poll()
            val currentCave = currentPath.last()

            if (currentCave == goal) {
                uniquePaths.add(currentPath.joinToString { it.value.label })
            }
            if (currentPath.count { it == caveAllowedToVisitTwice } < 2) {
                caveAllowedToVisitTwice.value.allowedToVisitTwice = true
            }
            for (neighbor in currentCave.neighbors) {
                val isBigCave = neighbor.value.label?.all(Char::isUpperCase) == true
                val allowedToVisitTwice = neighbor.value.allowedToVisitTwice == true &&
                        currentPath.count { it == caveAllowedToVisitTwice } < 2
                if (neighbor !in currentPath || isBigCave || allowedToVisitTwice) {
                    val newPath = currentPath.plus(neighbor)
                    pathsQueue.offer(newPath)
                }
            }
            currentCave.value.allowedToVisitTwice = false
        }
        return uniquePaths
    }

    internal data class Cave(val label: String, var allowedToVisitTwice: Boolean)

    fun part1Elizarov(): Int = day12Elizarov(lines).size
    fun part2Elizarov(): Int = day12Elizarov(lines, visitSmallCaveTwice = true).size
}

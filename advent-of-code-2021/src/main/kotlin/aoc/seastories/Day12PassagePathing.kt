package aoc.seastories

import aoc.utils.allPathsByDfs
import aoc.utils.linkedListOf
import aoc.utils.model.Node
import aoc.utils.toBiDiGraph
import java.io.File


internal class Day12PassagePathing(private val inputPath: String) : ChallengeDay {

    override fun part1(): Int = File(inputPath).readLines().toBiDiGraph('-').countDistinctPaths()

    private fun Map<String, Node<String>>.countDistinctPaths(): Int = let { graph ->
        val start = graph["start"] ?: error("start not found")
        val end = graph["end"] ?: error("end not found")
        return allPathsByDfs(start, end) { it.value?.all(Char::isLowerCase) == true }.count()
    }

    override fun part2(): Int {
        val graph = File(inputPath).readLines().toBiDiGraph("-") { label -> Cave(label, allowedToVisitTwice = false) }
        val start = graph["start"] ?: error("start not found")
        val end = graph["end"] ?: error("end not found")

        return graph.filter { (label) -> label != "start" && label != "end" && label.all(Char::isLowerCase) }
            .flatMap { (_, allowedToVisitTwice) -> findPathsByBfs(start, end, allowedToVisitTwice) }
            .distinct()
            .count()
    }

    private fun findPathsByBfs(src: Node<Cave>, goal: Node<Cave>, caveAllowedToVisitTwice: Node<Cave>): Set<String> {
        val uniquePaths = mutableSetOf<String>()

        val pathsQueue = linkedListOf(listOf(src))
        while (pathsQueue.isNotEmpty()) {
            val currentPath = pathsQueue.poll()
            val currentCave = currentPath.last()

            if (currentCave == goal) {
                uniquePaths.add(currentPath.joinToString { it.value?.label ?: "" })
            }
            if (currentPath.count { it == caveAllowedToVisitTwice } < 2) {
                caveAllowedToVisitTwice.value?.allowedToVisitTwice = true
            }
            for (neighbor in currentCave.neighbors) {
                val isBigCave = neighbor.value?.label?.all(Char::isUpperCase) == true
                val allowedToVisitTwice = neighbor.value?.allowedToVisitTwice == true &&
                        currentPath.count { it == caveAllowedToVisitTwice } < 2
                if (neighbor !in currentPath || isBigCave || allowedToVisitTwice) {
                    val newPath = currentPath.plus(neighbor)
                    pathsQueue.offer(newPath)
                }
            }
            currentCave.value?.allowedToVisitTwice = false
        }
        return uniquePaths
    }

    internal data class Cave(val label: String, var allowedToVisitTwice: Boolean)
}

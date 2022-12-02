package aoc

import model.Node
import utils.allPathsByDfs
import utils.toBiDiGraph
import java.io.File
import java.util.*


internal object Day12PassagePathing : ChallengeDay {

    fun part1(path: String): Int = File(path).readLines().toBiDiGraph('-').countDistinctPaths()

    private fun Map<String, Node<String?>>.countDistinctPaths(): Int = let { graph ->
        val start = graph["start"] ?: throw IllegalStateException()
        val end = graph["end"] ?: throw IllegalStateException()
        return allPathsByDfs(start, end) { it.value?.all(Char::isLowerCase) ?: false }.count()
    }

    fun part2(path: String): Int {
        val graph = File(path).readLines().toBiDiGraph("-") { label -> Cave(label, false) }
        val start = graph["start"] ?: throw IllegalStateException()
        val end = graph["end"] ?: throw IllegalStateException()

        return graph.filter { (label) -> label != "start" && label != "end" && label.all(Char::isLowerCase) }
            .flatMap { (_, allowedToVisitTwice) -> findPathsByBfs(start, end, allowedToVisitTwice) }
            .distinct()
            .count()
    }

    private fun findPathsByBfs(src: Node<Cave>, goal: Node<Cave>, caveAllowedToVisitTwice: Node<Cave>): Set<String> {
        val uniquePaths = mutableSetOf<String>()

        val pathsQueue: Queue<List<Node<Cave>>> = LinkedList()
        pathsQueue.offer(mutableListOf(src))
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
                val isBigCave = neighbor.value?.label?.all(Char::isUpperCase) ?: false
                val allowedToVisitTwice = neighbor.value?.allowedToVisitTwice ?: false &&
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

    override fun part1() = part1(ChallengeDay.inputDir + "/day12.txt")
    override fun part2() = part2(ChallengeDay.inputDir + "/day12.txt")

    internal data class Cave(val label: String, var allowedToVisitTwice: Boolean)
}

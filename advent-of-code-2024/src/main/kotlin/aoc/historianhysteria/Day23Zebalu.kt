package aoc.historianhysteria

import aoc.utils.invoke
import kotlin.collections.asSequence
import kotlin.collections.filter

class Day23Zebalu(input: List<String>) {
    private val network: Map<String, MutableSet<String>> = buildMap {
        input.forEach {
            val (c1, c2) = it.split("-")
            computeIfAbsent(c1) { HashSet() }.add(c2)
            computeIfAbsent(c2) { HashSet() }.add(c1)
        }
    }

    fun part1(): Int = interconnectedTriosWithStartingT().size
    fun part2(): String = findGroups().maxBy { it.size }.sorted().joinToString(",")

    private fun interconnectedTriosWithStartingT(): Set<Set<String>> = buildSet {
        network.keys.asSequence()
            .filter { it.startsWith("t") }
            .forEach { firstNode ->
                network(firstNode).filter { secondNode -> secondNode != firstNode }
                    .forEach { secondNode ->
                        network(secondNode).asSequence()
                            .filter { thirdNode -> thirdNode != firstNode && firstNode in network(thirdNode) }
                            .forEach { thirdNode -> add(setOf(firstNode, secondNode, thirdNode)) }
                    }
            }
    }

    private fun findGroups(): List<Set<String>> = buildList {
        val processed = HashSet<String>()
        val seen = HashSet<Set<String>>()
        val queue = ArrayDeque<Set<String>>()
        for (firstNode in network.keys) {
            queue.clear()
            seen.clear()
            queue.add(setOf(firstNode))
            seen.add(queue.first())
            while (queue.isNotEmpty()) {
                val nodes = queue.removeFirst()
                var extended = false
                for (connected in network(nodes.last())) {
                    if (connected !in processed && network(connected).containsAll(nodes)) {
                        val next = nodes + connected
                        if (seen.add(next)) {
                            queue.add(next)
                            extended = true
                        }
                    }
                }
                if (!extended && nodes.size > 1) {
                    add(nodes)
                }
            }
            processed.add(firstNode)
        }
    }
}

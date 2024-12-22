package aoc.seastories

import aoc.utils.graph.Node
import aoc.utils.invoke

/**
 * https://github.com/elizarov/AdventOfCode2021/blob/main/src/Day12_1.kt
 *
 * A type of depth first search
 *
 * [aoc.utils.Tag.RECURSIVE], [aoc.utils.Tag.PATH_SEARCH]
 */
fun day12Elizarov(input: List<String>, visitSmallCaveTwice: Boolean = false) = buildList {
    val g = buildGraph(input)
    val visited = HashSet<String>()
    fun find(cave: Cave, dontVisitSmall: Boolean = false) {
        if (cave.label == "end") {
            add(cave)
            return
        }
        for (other in g(cave.label)) {
            if (other.label == "start") continue
            val small = other.label[0] in 'a'..'z'
            var nvt = dontVisitSmall
            if (small) {
                if (other.label in visited) {
                    if (dontVisitSmall) continue
                    nvt = true
                } else {
                    visited += other.label
                }
            }
            find(Cave(other.label, prev = cave), dontVisitSmall = nvt)
            if (small && nvt == dontVisitSmall) visited -= other.label
        }
    }
    find(Cave("start"), dontVisitSmall = !visitSmallCaveTwice)
}

private fun buildGraph(input: List<String>): Map<String, HashSet<Cave>> = buildMap {
    for (s in input) {
        val (a, b) = s.split("-")
        getOrPut(a) { HashSet() }.add(Cave(b))
        getOrPut(b) { HashSet() }.add(Cave(a))
    }
}

data class Cave(val label: String, override val prev: Cave? = null) : Node<Cave>

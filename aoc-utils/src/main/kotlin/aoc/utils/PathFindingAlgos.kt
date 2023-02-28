package aoc.utils

import aoc.utils.model.Node
import aoc.utils.model.WeightedNode

infix fun <T> WeightedNode<T>.dijkstra(goal: WeightedNode<T>): WeightedNode<T> {
    val settled = mutableSetOf<WeightedNode<T>>()
    val unsettled = mutableSetOf(this.apply { cost = 0 })
    while (unsettled.isNotEmpty()) {
        val current = unsettled.minByOrNull(WeightedNode<T>::cost) ?: break
        for (neighbor in current.neighbors) {
            if (neighbor !in settled) {
                neighbor.updateShortestPath(current)
                unsettled += neighbor
            }
        }
        if (current == goal) return goal
        unsettled -= current
        settled += current
    }
    error("no path to $goal found")
}

fun <T> allPathsByDfs(start: Node<T>, goal: Node<T>, predicate: (Node<T>) -> Boolean): List<List<Node<T>>> =
    mutableListOf<List<Node<T>>>().apply { dfsRecursive(start, goal, predicate = predicate) }

private fun <T> MutableList<List<Node<T>>>.dfsRecursive(
    current: Node<T>,
    goal: Node<T>,
    visited: MutableSet<Node<T>> = mutableSetOf(),
    localPathList: MutableList<Node<T>> = mutableListOf(current),
    predicate: (Node<T>) -> Boolean
) {
    if (current == goal) {
        this += localPathList.toList()
        return
    }
    if (predicate(current)) {
        visited += current
    }
    for (neighbor in current.neighbors) {
        if (neighbor !in visited) {
            localPathList += neighbor
            dfsRecursive(neighbor, goal, visited, localPathList, predicate)
            localPathList -= neighbor
        }
    }
    if (predicate(current)) {
        visited -= current
    }
}

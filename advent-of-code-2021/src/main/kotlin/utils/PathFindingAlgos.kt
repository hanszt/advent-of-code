package utils

import model.Node
import model.WeightedNode

fun <T> WeightedNode<T>.dijkstra(goal: WeightedNode<T>): WeightedNode<T> {
    val settled = mutableSetOf<WeightedNode<T>>()
    val unsettled = mutableSetOf(this.apply { distance = 0 })
    while (unsettled.isNotEmpty()) {
        val current = unsettled.minByOrNull(WeightedNode<T>::distance) ?: break
        for (neighbor in current.neighbors) {
            if (neighbor !in settled && neighbor is WeightedNode) {
                neighbor.updateShortestPath(current)
                unsettled.add(neighbor)
            }
        }
        if (current == goal) return current
        unsettled.remove(current)
        settled.add(current)
    }
    throw IllegalStateException("no path to $goal found")
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
        this.add(localPathList.toList())
        return
    }
    if (predicate(current)) {
        visited.add(current)
    }
    for (neighbor in current.neighbors) {
        if (neighbor !in visited) {
            localPathList.add(neighbor)
            dfsRecursive(neighbor, goal, visited, localPathList, predicate)
            localPathList.remove(neighbor)
        }
    }
    if (predicate(current)) {
        visited.remove(current)
    }
}

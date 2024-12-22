package aoc.utils.graph

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

/**
 * See https://www.geeksforgeeks.org/find-paths-given-source-destination/
 */
fun <T> allPathsByDfs(start: MutableNode<T>, goal: MutableNode<T>, predicate: (MutableNode<T>) -> Boolean): Sequence<List<MutableNode<T>>> = sequence {
    dfsRecursive(start, goal, predicate = predicate)
}

private suspend fun <T> SequenceScope<List<MutableNode<T>>>.dfsRecursive(
    current: MutableNode<T>,
    goal: MutableNode<T>,
    visited: MutableSet<MutableNode<T>> = HashSet(),
    localPath: MutableList<MutableNode<T>> = mutableListOf(current),
    predicate: (MutableNode<T>) -> Boolean = { true }
) {
    if (current == goal) {
        yield(localPath.toList())
        return
    }
    if (predicate(current)) {
        visited += current
    }
    for (neighbor in current.neighbors) {
        if (neighbor !in visited) {
            localPath += neighbor
            dfsRecursive(neighbor, goal, visited, localPath, predicate)
            localPath -= neighbor
        }
    }
    if (predicate(current)) {
        visited -= current
    }
}

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
fun <N : Node<N>> allPathsByDfs(start: N, goal: N, predicate: (N) -> Boolean): Sequence<List<N>> = sequence {
    this.allPathsByDfs(start, goal, predicate = predicate)
}

private suspend fun <N : Node<N>> SequenceScope<List<N>>.allPathsByDfs(
    current: N,
    goal: N,
    visited: MutableSet<N> = HashSet(),
    localPath: MutableList<N> = mutableListOf(current),
    predicate: (N) -> Boolean = { true }
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
            allPathsByDfs(neighbor, goal, visited, localPath, predicate)
            localPath -= neighbor
        }
    }
    if (predicate(current)) {
        visited -= current
    }
}

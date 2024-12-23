package aoc.utils.graph

interface Node<N : Node<N>> {
    /**
     * The neighbor node this node is explored from
     */
    val prev: N?

    /**
     * Backtracks a path formed by all nodes linked to this node. If it contains a cycle, it will break
     */
    fun <R> traceBack(transform: (N) -> R): List<R> = buildList {
        val visited = mutableSetOf<N>()
        var current = this@Node
        while (true) {
            @Suppress("UNCHECKED_CAST")
            val n = current as N
            if (!visited.add(n)) break
            add(transform(n))
            current = current.prev ?: break
        }
    }
}
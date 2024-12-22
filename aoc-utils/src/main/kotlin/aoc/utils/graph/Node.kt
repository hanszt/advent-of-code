package aoc.utils.graph

interface Node<N : Node<N>> {
    /**
     * The neighbor node this node is explored from
     */
    val prev: N?

    /**
     * Backtracks a path formed by all nodes linked to this node
     */
    fun <R> traceBackToStart(transform: (N) -> R): List<R> = buildList {
        var current = this@Node
        while (true) {
            @Suppress("UNCHECKED_CAST")
            add(transform(current as N))
            current = current.prev ?: break
        }
    }
}
package aoc.utils.graph

interface Node<N : Node<N>> {
    /**
     * The node this node is explored from
     */
    val prev: N?

    /**
     * Backtracks a path formed by all nodes linked to this node. If it contains a cycle, it will break.
     */
    fun <R> traceBack(transform: (N) -> R): Sequence<R> = Sequence {
        val visited = HashSet<N>()
        var current: Node<N>? = this@Node
        object : Iterator<R> {
            override fun hasNext(): Boolean = current != null && current !in visited

            @Suppress("UNCHECKED_CAST")
            override fun next(): R = if (hasNext()) {
                val c = current as N
                visited.add(c)
                current = current?.prev
                transform(c)
            } else throw NoSuchElementException()
        }
    }
}
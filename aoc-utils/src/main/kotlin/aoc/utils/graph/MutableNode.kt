package aoc.utils.graph

class MutableNode<T>(val value: T, override val prev: MutableNode<T>? = null) : Node<MutableNode<T>> {

    private val neighborsInternal = HashSet<MutableNode<T>>()
    override val neighbors: Set<MutableNode<T>> get() = neighborsInternal.toSet()

    fun addNeighbor(neighbor: MutableNode<T>) = neighborsInternal.add(neighbor)

    override fun toString(): String = "Node(value=$value, neighbors=${neighborsInternal.map(MutableNode<T>::value)})"
}
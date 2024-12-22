package aoc.utils.graph

import java.util.Collections

class MutableNode<T>(val value: T, override val prev: MutableNode<T>? = null) : Node<MutableNode<T>> {

    private val neighborsInternal = HashSet<MutableNode<T>>()
    val neighbors: Set<MutableNode<T>> get() = Collections.unmodifiableSet(neighborsInternal)

    fun addNeighbor(neighbor: MutableNode<T>) = neighborsInternal.add(neighbor)

    override fun toString(): String = "Node(value=$value, neighbors=${neighborsInternal.map(MutableNode<T>::value)})"
}
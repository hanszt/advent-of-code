package aoc.utils.graph

import java.util.Collections

class Node<T>(val value: T? = null) {

    private val neighborsInternal = HashSet<Node<T>>()
    val neighbors: Set<Node<T>> get() = Collections.unmodifiableSet(neighborsInternal)

    fun addNeighbor(neighbor: Node<T>) = neighborsInternal.add(neighbor)

    override fun toString(): String = "Node(value=$value, neighbors=${neighborsInternal.map(Node<T>::value)})"
}
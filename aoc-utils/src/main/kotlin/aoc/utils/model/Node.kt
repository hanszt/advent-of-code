package aoc.utils.model

import java.util.*

class Node<T>(val value: T? = null) {

    private val neighborsInternal = HashSet<Node<T>>()
    val neighbors: Set<Node<T>> get() = Collections.unmodifiableSet(neighborsInternal)

    fun addNeighbor(neighbor: Node<T>) = neighborsInternal.add(neighbor)
    fun addNeighbors(neighbors: Iterable<Node<T>>) = neighbors.forEach(neighborsInternal::add)


    override fun toString(): String = "Node(value=$value, neighbors=${neighborsInternal.map(Node<T>::value)})"
}

package aoc.utils.model

import java.util.*

open class Node<T>(val value: T? = null) {

    val neighbors =  mutableSetOf<Node<T>>()

    fun addNeighbor(neighbor: Node<T>) = neighbors.add(neighbor)

    override fun toString(): String = "Node(value=$value, neighbors=${neighbors.map(Node<T>::value)})"
}

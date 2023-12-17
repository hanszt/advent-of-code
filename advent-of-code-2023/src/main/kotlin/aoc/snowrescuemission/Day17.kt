package aoc.snowrescuemission

import aoc.utils.ChallengeDay
import aoc.utils.invoke
import aoc.utils.model.GridPoint2D
import aoc.utils.model.gridPoint2D
import java.io.File
import java.util.PriorityQueue

class Day17(
    fileName: String? = null,
    private val grid: List<String> = File(fileName ?: error("No fileName or text provided")).readLines()
) : ChallengeDay {

    private val positionsToNodes = grid
        .flatMapIndexed { y, n ->
            n.mapIndexed { x, c ->
                val point2D = gridPoint2D(x, y)
                point2D to Node(cost = c.digitToInt(), position = point2D)
            }
        }
        .associate { it }

    override fun part1(): Int {
        dijkstra(positionsToNodes(GridPoint2D.ZERO))
        return positionsToNodes(gridPoint2D(grid[0].lastIndex, grid.lastIndex)).distance
    }

    private fun dijkstra(start: Node) {
        val settled = HashSet<Node>()
        val priorityQueue = PriorityQueue(compareBy(Node::cost)).apply { add(start) }
        start.distance = 0
        while (priorityQueue.isNotEmpty()) {
            val node = priorityQueue.remove()
            if (settled.add(node)) {
                val neighbors = node.position.neighbors()
                for (neighbor in neighbors) {
                    if (neighbor !in settled) {
                        val edgeDistance = neighbor.cost
                        val newDistance = node.distance + edgeDistance

                        if (newDistance < neighbor.distance) {
                            neighbor.distance = newDistance
                        }
                        priorityQueue.add(neighbor)
                    }
                }
            }
        }
    }

    private fun GridPoint2D.neighbors(): List<Node> = GridPoint2D.orthoDirs.mapNotNull {
        positionsToNodes[gridPoint2D(x + it.x, y + it.y)]
    }

    data class Node(
        val cost: Int,
        val position: GridPoint2D,
        var distance: Int = Int.MAX_VALUE,
    )

    override fun part2(): Int {
        TODO()
    }
}

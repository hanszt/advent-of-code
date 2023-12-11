package aoc.snowrescuemission

import aoc.utils.ChallengeDay
import aoc.utils.model.GridPoint2D
import aoc.utils.model.gridPoint2D
import java.io.File

class Day10(
    fileName: String? = null,
    area: String = File(fileName ?: error("No fileName or text provided")).readText()
) : ChallengeDay {

    private val grid = area.lines()
    private val graph = graph(grid)
    private val start = graph.entries.first { (_, c) -> c == 'S' }.key

    override fun part1(): Int = bfs(start).last().toPath().size - 1

    override fun part2(): Int {
        val mask = Array(grid.size) { IntArray(grid[0].length) }
        cyclesByDfs(start)
            .map { it.toPath() }
            .maxBy(List<*>::size)
            .onEach { println(it) }
            .forEach { (x, y) -> mask[y][x] = 2 }
        mask.forEach { row -> println(row.joinToString("")) }

        return 0
    }

    private fun bfs(start: GridPoint2D): Sequence<Link> = sequence {
        val queue = mutableListOf(Link(start))
        val visited = mutableSetOf(start)
        while (queue.isNotEmpty()) {
            val cur = queue.removeFirst()
            val neighbors = cur.position.neighbors()
            for (neighbor in neighbors) {
                if (neighbor !in visited) {
                    visited += neighbor
                    val link = Link(neighbor, cur)
                    queue.addLast(link)
                    yield(link)
                }
            }
        }
    }

    private fun cyclesByDfs(start: GridPoint2D) = sequence {
        val visited = mutableSetOf<GridPoint2D>()
        val stack = mutableListOf(Link(start))
        while (stack.isNotEmpty()) {
            val cur = stack.removeLast()
            val position = cur.position
            if (position !in visited) {
                visited += position
                val neighbors = position.neighbors()
                for (neighbor in neighbors) {
                    if (neighbor == this@Day10.start) {
                        yield(cur)
                    }
                    val link = Link(neighbor, cur)
                    stack.addLast(link)
                }
            }
        }
    }

    private fun Link.toPath(): List<GridPoint2D> {
        var cur = this
        val path = mutableListOf(position)
        while (true) {
            cur = cur.origin ?: break
            path.addFirst(cur.position)
        }
        return path
    }

    data class Link(val position: GridPoint2D, val origin: Link? = null)

    private fun graph(rows: List<String>): Map<GridPoint2D, Char> {
        val graph = mutableMapOf<GridPoint2D, Char>()
        for ((y, row) in rows.withIndex()) {
            for ((x, c) in row.withIndex()) {
                val position = gridPoint2D(x, y)
                graph[position] = c
            }
        }
        return graph
    }

    private fun GridPoint2D.neighbors(): Set<GridPoint2D> {
        val neighbors = mutableSetOf<GridPoint2D>()
        for (dir in GridPoint2D.orthoDirs) {
            val nx = x + dir.x
            val ny = y + dir.y
            grid.getOrNull(ny)?.getOrNull(nx)?.let {
                if (it isNeighbor dir) {
                    neighbors += gridPoint2D(nx, ny)
                }
            }
        }
        return neighbors
    }

    private infix fun Char.isNeighbor(dir: GridPoint2D) = when {
        this == 'S' -> true
        dir.x == 1 -> this == '-' || this == '7' || this == 'J'
        dir.x == -1 -> this == '-' || this == 'F' || this == 'L'
        dir.y == 1 -> this == '|' || this == 'L' || this == 'J'
        dir.y == -1 -> this == '|' || this == '7' || this == 'F'
        else -> false
    }
}

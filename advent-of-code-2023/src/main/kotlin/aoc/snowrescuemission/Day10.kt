package aoc.snowrescuemission

import aoc.utils.CYAN
import aoc.utils.ChallengeDay
import aoc.utils.PIPE_DOWN_LEFT
import aoc.utils.PIPE_DOWN_RIGHT
import aoc.utils.PIPE_LEFT_RIGHT
import aoc.utils.PIPE_UP_DOWN
import aoc.utils.PIPE_UP_LEFT
import aoc.utils.PIPE_UP_RIGHT
import aoc.utils.gridAsString
import aoc.utils.model.GridPoint2D
import aoc.utils.model.gridPoint2D
import aoc.utils.withColor
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
        val mask = grid.map { it.map(Char::toString).toTypedArray() }.toTypedArray()
        cyclesByDfs(start)
            .onEach { println(it.position) }
            .map(Link::toPath)
            .maxBy(List<*>::size)
            .forEach { (x, y) -> mask[y][x] = grid[y][x].toPathCharacter().withColor(CYAN) }

        println(mask.gridAsString(spacing = 1))

        return countEnclosedTiles(mask)
    }

    private fun countEnclosedTiles(mask: Array<Array<String>>): Int {
        var nrOfEnclosedTiles = 0
        for (y in 1..<mask.lastIndex) {
            val r = mask[y]
            var isEnclosed = false
            for (x in 1..<r.lastIndex) {
                val prev = r[x - 1]
                val tile = r[x]
                val next = r[x + 1]
            }
        }
        return nrOfEnclosedTiles
    }

    private fun Char.toPathCharacter() = when(this) {
        'J' -> PIPE_UP_LEFT
        'L' -> PIPE_UP_RIGHT
        'F' -> PIPE_DOWN_RIGHT
        '7' -> PIPE_DOWN_LEFT
        '-' -> PIPE_LEFT_RIGHT
        '|' -> PIPE_UP_DOWN
        else -> this
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

    private data class Link(val position: GridPoint2D, val origin: Link? = null) {

        fun toPath(): List<GridPoint2D> = buildList {
            var cur = this@Link
            add(cur.position)
            while (true) {
                cur = cur.origin ?: break
                addFirst(cur.position)
            }
        }
    }

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

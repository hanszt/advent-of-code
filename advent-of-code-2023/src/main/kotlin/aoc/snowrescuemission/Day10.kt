package aoc.snowrescuemission

import aoc.utils.CYAN
import aoc.utils.ChallengeDay
import aoc.utils.PIPE_DOWN_LEFT
import aoc.utils.PIPE_DOWN_RIGHT
import aoc.utils.PIPE_LEFT_RIGHT
import aoc.utils.PIPE_UP_DOWN
import aoc.utils.PIPE_UP_LEFT
import aoc.utils.PIPE_UP_RIGHT
import aoc.utils.YELLOW_BG
import aoc.utils.gridAsString
import aoc.utils.linkedListOf
import aoc.utils.model.GridPoint2D
import aoc.utils.model.gridPoint2D
import aoc.utils.withColor
import java.io.File

class Day10(
    fileName: String? = null,
    area: String = File(fileName ?: error("No fileName or text provided")).readText()
) : ChallengeDay {

    private companion object {
        private const val START_CHAR = 'S'
        private const val ENCLOSED = ' '
        val pathComponents =
            setOf(PIPE_UP_DOWN, PIPE_UP_RIGHT, PIPE_UP_LEFT, PIPE_LEFT_RIGHT, PIPE_DOWN_LEFT, PIPE_DOWN_RIGHT, START_CHAR)
    }

    private val grid = area.lines()
    private val graph = graph(grid)
    private val start = graph.entries.first { (_, c) -> c == START_CHAR }.key

    override fun part1(): Int = bfs(start).last().toPath().size - 1

    override fun part2(): Int {
        val mask = grid.map { CharArray(it.length) { '.' } }.toTypedArray()
        cyclesByDfs(start)
            .map(Link::toPath)
            .maxBy(List<*>::size)
            .forEach { (x, y) -> mask[y][x] = grid[y][x].toPathCharacter() }
        return countEnclosedTiles(mask).also { println(mask.toColoredMap()) }
    }

    private fun Array<CharArray>.toColoredMap(): String = map { row ->
        row.map {
            when (it) {
                in pathComponents -> it.withColor(CYAN)
                ENCLOSED -> it.withColor(YELLOW_BG)
                else -> it.toString()
            }
        }
    }.gridAsString(spacing = 1)

    private fun countEnclosedTiles(mask: Array<CharArray>): Int {
        // Included when looking horizontally below middle
        val downConnected = setOf(PIPE_UP_DOWN, PIPE_DOWN_LEFT, PIPE_DOWN_RIGHT)
        val lowerWallTiles = if (startDownConnected()) downConnected.plus(START_CHAR) else downConnected
        var nrOfEnclosedTiles = 0
        for (y in 1..<mask.lastIndex) {
            val row = mask[y]
            for (x in 1..<row.lastIndex) {
                val c = row[x]
                if (c !in pathComponents) {
                    val isEnclosed = (x..<row.size).count { row[it] in lowerWallTiles } % 2 != 0
                    if (isEnclosed) {
                        mask[y][x] = ENCLOSED
                        nrOfEnclosedTiles++
                    }
                }
            }
        }
        return nrOfEnclosedTiles
    }

    private fun Char.toPathCharacter() = when (this) {
        'J' -> PIPE_UP_LEFT
        'L' -> PIPE_UP_RIGHT
        'F' -> PIPE_DOWN_RIGHT
        '7' -> PIPE_DOWN_LEFT
        '-' -> PIPE_LEFT_RIGHT
        '|' -> PIPE_UP_DOWN
        else -> this
    }

    private fun startDownConnected(): Boolean =
        grid.getOrNull(start.y + 1)?.getOrNull(start.x)?.toPathCharacter() in setOf(PIPE_UP_DOWN, PIPE_UP_LEFT, PIPE_UP_RIGHT)

    private fun bfs(start: GridPoint2D): Sequence<Link> = sequence {
        val visited = mutableSetOf(start)
        val queue = linkedListOf(Link(start))
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
                    stack.addLast(Link(neighbor, cur))
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

    private fun graph(rows: List<String>): Map<GridPoint2D, Char> = buildMap {
        for ((y, row) in rows.withIndex()) {
            for ((x, c) in row.withIndex()) {
                this[gridPoint2D(x, y)] = c
            }
        }
    }

    private fun GridPoint2D.neighbors(): Set<GridPoint2D> = buildSet {
        for (dir in GridPoint2D.orthoDirs) {
            val nx = x + dir.x
            val ny = y + dir.y
            grid.getOrNull(ny)?.getOrNull(nx)?.let {
                if (it isNeighbor dir) {
                    this += gridPoint2D(nx, ny)
                }
            }
        }
    }

    private infix fun Char.isNeighbor(dir: GridPoint2D) = when {
        this == START_CHAR -> true
        dir.x == 1 -> this == '-' || this == '7' || this == 'J'
        dir.x == -1 -> this == '-' || this == 'F' || this == 'L'
        dir.y == 1 -> this == '|' || this == 'L' || this == 'J'
        dir.y == -1 -> this == '|' || this == '7' || this == 'F'
        else -> false
    }
}

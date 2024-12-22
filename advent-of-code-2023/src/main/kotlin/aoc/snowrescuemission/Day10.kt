package aoc.snowrescuemission

import aoc.utils.*
import aoc.utils.grid2d.*
import java.io.File

class Day10(
    fileName: String? = null,
    area: String = File(fileName ?: error("No fileName or text provided")).readText()
) : ChallengeDay {

    private companion object {
        private const val START_CHAR = 'S'
        private const val ENCLOSED = ' '
        val pathComponents = setOf(
            PIPE_UP_DOWN, PIPE_UP_RIGHT, PIPE_UP_LEFT, PIPE_LEFT_RIGHT, PIPE_DOWN_LEFT, PIPE_DOWN_RIGHT, START_CHAR
        )
    }

    private val grid = area.lines()
    private val graph = graph(grid)
    private val start = graph.entries.first { (_, c) -> c == START_CHAR }.key

    /**
     * How many steps along the loop does it take to get from the starting position to the point farthest from the starting position?
     */
    override fun part1(): Int = floodFill(grid.dimension2D(), start) {
        val p = it.position
        grid[p].isNeighbor(dir = p - it.prev!!.position)
    }.last().cost

    /**
     * How many tiles are enclosed by the loop?
     */
    override fun part2(): Int {
        val mask = grid.toMutableCharGrid { '.' }
        dfs(start)
            .map { it.traceBackToStart { it.position } }
            .maxBy(List<*>::size)
            .forEach { mask[it] = grid[it].toPathCharacter() }
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

    private fun startDownConnected(): Boolean = grid.getOrNull(start.y + 1)
        ?.getOrNull(start.x)
        ?.toPathCharacter() in setOf(PIPE_UP_DOWN, PIPE_UP_LEFT, PIPE_UP_RIGHT)

    private fun dfs(start: GridPoint2D) = sequence {
        val visited = HashSet<GridPoint2D>()
        val stack = ArrayDeque<Grid2DNode>().also { it.addLast(Grid2DNode(start)) }
        while (stack.isNotEmpty()) {
            val cur = stack.removeLast()
            val p = cur.position
            if (p !in visited) {
                visited += p
                for (dir in GridPoint2D.towerDirs) {
                    val n = p + dir
                    grid.getOrNull(n)?.let {
                        if (it isNeighbor dir) {
                            if (n == this@Day10.start) {
                                yield(cur)
                            }
                            stack.addLast(Grid2DNode(n, prev = cur))
                        }
                    }
                }
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

    private infix fun Char.isNeighbor(dir: GridPoint2D) = when {
        this == START_CHAR -> true
        dir.x == 1 -> this == '-' || this == '7' || this == 'J'
        dir.x == -1 -> this == '-' || this == 'F' || this == 'L'
        dir.y == 1 -> this == '|' || this == 'L' || this == 'J'
        dir.y == -1 -> this == '|' || this == '7' || this == 'F'
        else -> false
    }
}

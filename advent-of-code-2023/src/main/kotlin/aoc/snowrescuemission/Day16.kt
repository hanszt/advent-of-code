package aoc.snowrescuemission

import aoc.utils.ChallengeDay
import aoc.utils.grid2d.GridPoint2D
import aoc.utils.grid2d.GridPoint2D.Companion.ZERO
import aoc.utils.grid2d.GridPoint2D.Companion.east
import aoc.utils.grid2d.GridPoint2D.Companion.north
import aoc.utils.grid2d.GridPoint2D.Companion.south
import aoc.utils.grid2d.GridPoint2D.Companion.west
import aoc.utils.grid2d.getOrNull
import aoc.utils.grid2d.set
import aoc.utils.grid2d.toMutableCharGrid
import java.io.File

class Day16(
    fileName: String? = null,
    private val grid: List<String> = File(fileName ?: error("No fileName or text provided")).readLines()
) : ChallengeDay {

    /**
     * The light isn't energizing enough tiles to produce lava; to debug the contraption, you need to start by analyzing the current situation.
     * With the beam starting in the top-left heading right, how many tiles end up being energized?
     */
    override fun part1(): Int = energizedLevel(ZERO, east)

    /**
     * Find the initial beam configuration that energizes the largest number of tiles; how many tiles are energized in that configuration?
     */
    override fun part2(): Int = buildList {
        var startPos = GridPoint2D(1, 0)
        val firstRow = grid[0]
        while (firstRow.getOrNull(startPos.x + 1) != null) {
            for (d in setOf(east, south, west)) {
                add(energizedLevel(startPos, d))
            }
            startPos = startPos.plusX(1)
        }
        startPos = GridPoint2D(firstRow.lastIndex, 1)
        while (grid.getOrNull(startPos.y + 1) != null) {
            for (d in setOf(south, west, north)) {
                add(energizedLevel(startPos, d))
            }
            startPos = startPos.plusY(1)
        }
        startPos = GridPoint2D(firstRow.lastIndex - 1, grid.lastIndex)
        while (firstRow.getOrNull(startPos.x - 1) != null) {
            for (d in setOf(west, north, east)) {
                add(energizedLevel(startPos, d))
            }
            startPos = startPos.plusX(-1)
        }
        startPos = GridPoint2D(0, grid.lastIndex - 1)
        while (grid.getOrNull(startPos.y - 1) != null) {
            for (d in setOf(north, east, south)) {
                add(energizedLevel(startPos, d))
            }
            startPos = startPos.plusY(-1)
        }
        add(energizedLevel(ZERO, east))
        add(energizedLevel(ZERO, south))
        add(energizedLevel(GridPoint2D(firstRow.lastIndex, 0), south))
        add(energizedLevel(GridPoint2D(firstRow.lastIndex, 0), west))
        add(energizedLevel(GridPoint2D(firstRow.lastIndex, grid.lastIndex), west))
        add(energizedLevel(GridPoint2D(firstRow.lastIndex, grid.lastIndex), north))
        add(energizedLevel(GridPoint2D(0, grid.lastIndex), north))
        add(energizedLevel(GridPoint2D(0, grid.lastIndex), east))
    }.max()

    private fun energizedLevel(startPos: GridPoint2D, startDir: GridPoint2D): Int {
        val mutableGrid = grid.toMutableCharGrid()
        propagateBeam(target = mutableGrid, startPos = startPos, startDir = startDir)
        return mutableGrid.flatMap(CharArray::toList).count { it == '#' }
    }


    private fun propagateBeam(
        target: Array<CharArray>,
        startPos: GridPoint2D,
        startDir: GridPoint2D,
        visitedSplitters: MutableSet<GridPoint2D> = HashSet()
    ) {
        var dir = startDir
        var pos = startPos
        while (true) {
            val c = grid.getOrNull(pos) ?: break
            target[pos] = '#'
            when {
                c == '\\' -> dir = GridPoint2D(dir.y, dir.x)
                c == '/' -> dir = GridPoint2D(-dir.y, -dir.x)
                c.isSplitter(dir) -> {
                    if (!visitedSplitters.add(pos)) break
                    val newDirL = dir.rot90L()
                    val newDirR = dir.rot90R()
                    propagateBeam(target, startPos = pos + newDirL, newDirL, visitedSplitters)
                    propagateBeam(target, startPos = pos + newDirR, newDirR, visitedSplitters)
                    break
                }
            }
            pos += dir
        }
    }

    private fun Char.isSplitter(dir: GridPoint2D) = (this == '|' && dir.x != 0) || (this == '-' && dir.y != 0)
}

package aoc.snowrescuemission

import aoc.utils.ChallengeDay
import aoc.utils.model.GridPoint2D
import aoc.utils.model.GridPoint2D.Companion.ZERO
import aoc.utils.model.GridPoint2D.Companion.east
import aoc.utils.model.GridPoint2D.Companion.north
import aoc.utils.model.GridPoint2D.Companion.south
import aoc.utils.model.GridPoint2D.Companion.west
import aoc.utils.model.gridPoint2D
import java.io.File

class Day16(
    fileName: String? = null,
    private val grid: List<String> = File(fileName ?: error("No fileName or text provided")).readLines()
) : ChallengeDay {

    override fun part1(): Int = energizedLevel(ZERO, east)
    override fun part2(): Int {
        val energizedValues = mutableListOf<Int>()
        var startPos = gridPoint2D(1, 0)
        val firstRow = grid[0]
        while (firstRow.getOrNull(startPos.x + 1) != null) {
            for (d in setOf(west, south, east)) {
                energizedValues.add(energizedLevel(startPos, d))
            }
            startPos = startPos.plusX(1)
        }
        startPos = gridPoint2D(firstRow.lastIndex, 1)
        while (grid.getOrNull(startPos.y + 1) != null) {
            for (d in setOf(north, west, south)) {
                energizedValues.add(energizedLevel(startPos, d))
            }
            startPos = startPos.plusY(1)
        }
        startPos = gridPoint2D(firstRow.lastIndex - 1, grid.lastIndex)
        while (firstRow.getOrNull(startPos.x - 1) != null) {
            for (d in setOf(west, north, east)) {
                energizedValues.add(energizedLevel(startPos, d))
            }
            startPos = startPos.plusX(-1)
        }
        startPos = gridPoint2D(0, grid.lastIndex - 1)
        while (grid.getOrNull(startPos.y - 1) != null) {
            for (d in setOf(north, east, south)) {
                energizedValues.add(energizedLevel(startPos, d))
            }
            startPos = startPos.plusY(-1)
        }
        energizedValues.add(energizedLevel(ZERO, east))
        energizedValues.add(energizedLevel(ZERO, south))
        energizedValues.add(energizedLevel(gridPoint2D(firstRow.lastIndex, 0), south))
        energizedValues.add(energizedLevel(gridPoint2D(firstRow.lastIndex, 0), west))
        energizedValues.add(energizedLevel(gridPoint2D(firstRow.lastIndex, grid.lastIndex), west))
        energizedValues.add(energizedLevel(gridPoint2D(firstRow.lastIndex, grid.lastIndex), north))
        energizedValues.add(energizedLevel(gridPoint2D(0, grid.lastIndex), north))
        energizedValues.add(energizedLevel(gridPoint2D(0, grid.lastIndex), east))
        return energizedValues.max()
    }

    private fun energizedLevel(startPos: GridPoint2D, startDir: GridPoint2D): Int {
        val mutableGrid = Array(grid.size) { grid[it].toCharArray() }
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
            val c = grid.getOrNull(pos.y)?.getOrNull(pos.x) ?: break
            target[pos.y][pos.x] = '#'
            when {
                c == '\\' -> dir = gridPoint2D(dir.y, dir.x)
                c == '/' -> dir = gridPoint2D(-dir.y, -dir.x)
                c isSplitter dir -> {
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

    private infix fun Char.isSplitter(dir: GridPoint2D) = (this == '|' && dir.x != 0) || (this == '-' && dir.y != 0)
}

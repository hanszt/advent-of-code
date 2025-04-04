@file:Suppress("DuplicatedCode")

package aoc.compose.day04

import androidx.compose.ui.graphics.Color
import aoc.utils.grid2d.getOrNull
import aoc.utils.relativeToResources
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlin.io.path.Path
import kotlin.io.path.readLines
import aoc.utils.grid2d.GridPoint2D as Vec2

data class ColorEvent(val loc: Vec2, val color: Color, val priority: Int)

val colorFlow = MutableSharedFlow<ColorEvent>()

data class Grid(val elems: List<String>) {
    val allowedDirections = listOf(
        Vec2(1, 0), // east
        Vec2(1, 1), // southeast
        Vec2(0, 1), // south
        Vec2(-1, 1), // southwest
        Vec2(-1, 0), // west
        Vec2(-1, -1), // northwest
        Vec2(0, -1), // north
        Vec2(1, -1), // northeast
    )

    val points = sequence {
        for (y in elems[0].indices) {
            for (x in elems.indices) {
                yield(Vec2(x, y))
            }
        }
    }

    suspend fun getAtPos(x: Int, y: Int, noDelay: Boolean = false): Char? = getAtPos(Vec2(x, y), noDelay)
    suspend fun getAtPos(vec2: Vec2, noDelay: Boolean = false): Char? {
        val elem = elems.getOrNull(vec2)
        if (elem != null) {
            colorFlow.emit(ColorEvent(vec2, Color(0xffe0e0e0), 99))
            if (!noDelay) {
                delay(100)
            }
        }
        return elem
    }

    suspend fun countXmasWordsForPosition(start: Vec2): Int {
        return allowedDirections.count { direction ->
            if (getAtPos(start, true) != 'X') {
                delay(50)
                return@count false
            }
            checkXmasWordForDirection(start, direction)
        }
    }

    private suspend fun checkXmasWordForDirection(start: Vec2, direction: Vec2): Boolean {
        var running = start
        if (getAtPos(running, true) != 'X') {
            return false
        }

        for (letter in listOf('X', 'M', 'A', 'S')) {
            if (getAtPos(running) != letter) {
                return false
            }
            colorFlow.emit(ColorEvent(running, Color(0xFFc4ffff), 50))
            running += direction
        }
        println("FOUND ONE at $start")

        running = start

        repeat(4) {
            colorFlow.emit(ColorEvent(running, Color.Cyan, 10))
            delay(25)
            running += direction
        }
        return true
    }

    suspend fun isMASCrossAtPosition(center: Vec2): Boolean = isMASCrossAtPosition(center.x, center.y)
    suspend fun isMASCrossAtPosition(centerX: Int, centerY: Int): Boolean {
        if (getAtPos(centerX, centerY) != 'A') {
            return false // invalid cross-center
        }
        // we start at the 'A' of 'MAS', because it is the center.
        val fdm = if (getAtPos(centerX - 1, centerY - 1) == 'M') {
            colorFlow.emit(ColorEvent(Vec2(centerX - 1, centerY - 1), almostStarColor, 80))
            true
        } else {
            false
        }
        val fds = if (getAtPos(centerX + 1, centerY + 1) == 'S') {
            colorFlow.emit(ColorEvent(Vec2(centerX + 1, centerY + 1), almostStarColor, 80))
            true
        } else {
            false
        }
        val isFallingDiagonalMAS = fdm && fds
        val isFallingDiagonalSAM =
            if (getAtPos(centerX - 1, centerY - 1) == 'S') {
                colorFlow.emit(ColorEvent(Vec2(centerX - 1, centerY - 1), almostStarColor, 80))
                true
            } else {
                false
            }
                    &&
                    if (getAtPos(centerX + 1, centerY + 1) == 'M') {
                        colorFlow.emit(ColorEvent(Vec2(centerX + 1, centerY + 1), almostStarColor, 80))
                        true
                    } else {
                        false
                    }
        val fallingDiagonalOk = isFallingDiagonalMAS || isFallingDiagonalSAM

        val isRM = if (getAtPos(centerX - 1, centerY + 1) == 'M') {
            colorFlow.emit(ColorEvent(Vec2(centerX - 1, centerY + 1), almostStarColor, 80))
            true
        } else {
            false
        }
        val isRS = if (getAtPos(centerX + 1, centerY - 1) == 'S') {
            colorFlow.emit(ColorEvent(Vec2(centerX + 1, centerY - 1), almostStarColor, 80))
            true
        } else {
            false
        }
        val isRisingDiagonalMAS = isRM && isRS
        val isRisingDiagonalSAM =
            if (getAtPos(centerX - 1, centerY + 1) == 'S') {
                colorFlow.emit(ColorEvent(Vec2(centerX - 1, centerY + 1), almostStarColor, 80))
                true
            } else {
                false
            }
                    &&
                    if (getAtPos(centerX + 1, centerY - 1) == 'M') {
                        colorFlow.emit(ColorEvent(Vec2(centerX + 1, centerY - 1), almostStarColor, 80))
                        true
                    } else {
                        false
                    }
        val risingDiagonalOk = isRisingDiagonalMAS || isRisingDiagonalSAM
        val totallyOk = fallingDiagonalOk && risingDiagonalOk
        if (totallyOk) {
            colorFlow.emit(ColorEvent(Vec2(centerX, centerY), starCenterColor, 1))
            colorFlow.emit(ColorEvent(Vec2(centerX - 1, centerY + 1), starArmColor, 1))
            colorFlow.emit(ColorEvent(Vec2(centerX - 1, centerY - 1), starArmColor, 1))
            colorFlow.emit(ColorEvent(Vec2(centerX + 1, centerY + 1), starArmColor, 1))
            colorFlow.emit(ColorEvent(Vec2(centerX + 1, centerY - 1), starArmColor, 1))
        }
        return totallyOk
    }
}

val starCenterColor = Color(0xFF00c0ff)
val starArmColor = Color(0xff0086b3)
val almostStarColor = Color(0xFF004d67)

val input by lazy {
    Path(
        {}.relativeToResources(
            resourcePath = "/app-props.txt",
            rootFileName = "advent-of-code-2024",
            inputFileName = "input"
        ) + "/day04.txt"
    ).readLines()
}

suspend fun main() {
    val grid = Grid(input)
    part1(grid)
    part2(grid)
}

private suspend fun part1(grid: Grid) {
    println(grid.points.sumOf { point -> grid.countXmasWordsForPosition(point) })
}

private suspend fun part2(grid: Grid) {
    println(grid.points.count { grid.isMASCrossAtPosition(it) })
}

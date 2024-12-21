package aoc.seastories

import aoc.utils.grid2d.toMutableGrid
import java.io.File

internal class Day25SeaCucumber(private val inputPath: String) : ChallengeDay {

    override fun part1(): Int = File(inputPath).readLines().toMutableGrid { it }.simulateAndCount()
    override fun part2() = "To get the key, all fifty stars are required"

    private fun Array<Array<Char>>.move(cucumber: Char, dx: Int, dy: Int): Boolean {
        val booleanGrid = Array(size) { BooleanArray(first().size) }
        var moved = false
        for (y in indices) {
            for (x in 0 until first().size) {
                booleanGrid[y][x] = this[y][x] == cucumber && this[(y + dy) % size][(x + dx) % first().size] == '.'
                if (booleanGrid[y][x]) {
                    moved = true
                }
            }
        }
        for (y in indices) {
            for (x in 0 until first().size) {
                if (booleanGrid[y][x]) {
                    this[y][x] = '.'
                    this[(y + dy) % size][(x + dx) % first().size] = cucumber
                }
            }
        }
        return moved
    }

    private fun Array<Array<Char>>.simulateAndCount() = generateSequence { }
        .takeWhile { moved(this) }
        .count() + 1

    private fun moved(grid: Array<Array<Char>>): Boolean {
        val movedX = grid.move('>', 1, 0)
        val movedY = grid.move('v', 0, 1)
        return movedX || movedY
    }
}

package aoc.seastories

import java.io.File
import aoc.utils.toGridOf

internal object Day25SeaCucumber : ChallengeDay {

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

    fun part1(path: String): Int = File(path).readLines().toGridOf { it }.simulateAndCount()

    private fun Array<Array<Char>>.simulateAndCount() = generateSequence { }
        .takeWhile { moved(this) }
        .count() + 1

    private fun moved(grid: Array<Array<Char>>): Boolean {
        val movedX = grid.move('>', 1, 0)
        val movedY = grid.move('v', 0, 1)
        return movedX || movedY
    }

    override fun part1() = part1(ChallengeDay.inputDir + "/day25.txt")
    override fun part2() = "To get the key, all fifty stars are required"
}

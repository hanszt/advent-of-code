package aoc.seastories

import aoc.utils.oneOrMoreWhiteSpaces
import aoc.utils.rotated
import aoc.utils.splitByBlankLine
import aoc.utils.toIntGrid
import java.io.File

internal class Day04GiantSquid(private val inputPath: String) : ChallengeDay {

    override fun part1(): Int {
        val (boards, allNrsToDrawList) = File(inputPath).toBoardsAndNrsToDrawList()
        val drawnNrs = allNrsToDrawList.slice(0..3).toMutableList()
        var firstWinning: Array<IntArray> = emptyArray()
        while (firstWinning.isEmpty()) {
            drawnNrs.add(allNrsToDrawList[drawnNrs.size])
            firstWinning = boards.firstOrNull { it.isWinningBoard(drawnNrs) } ?: emptyArray()
        }
        return firstWinning.sumUnmarkedNrs(drawnNrs) * drawnNrs.last()
    }

    override fun part2(): Int {
        val (boards, allNrToBeDrawn) = File(inputPath).toBoardsAndNrsToDrawList()
        val drawnNrs = allNrToBeDrawn.slice(0..3).toMutableList()
        val boardsInGame = boards.toMutableList()
        val boardsWon = LinkedHashSet<Array<IntArray>>()
        while (boardsWon.size != boards.size) {
            drawnNrs.add(allNrToBeDrawn[drawnNrs.size])
            boardsInGame.filterTo(boardsWon) { it.isWinningBoard(drawnNrs) }
            boardsInGame.removeAll(boardsWon)
        }
        return boardsWon.last().sumUnmarkedNrs(drawnNrs) * drawnNrs.last()
    }

    private fun Array<IntArray>.sumUnmarkedNrs(drawnNrs: List<Int>) = asSequence()
        .flatMap(IntArray::toList)
        .filterNot { it in drawnNrs }.sum()

    private fun File.toBoardsAndNrsToDrawList(): Pair<List<Array<IntArray>>, List<Int>> =
        readText().splitByBlankLine()
            .run {
                slice(1..<size)
                    .map { it.lines().toIntGrid(oneOrMoreWhiteSpaces) } to nrsToDrawList()
            }

    private fun List<String>.nrsToDrawList() = first().split(",").map(String::toInt)

    companion object {
        fun Array<IntArray>.isWinningBoard(drawnNumbers: List<Int>): Boolean =
            any { row -> row.all(drawnNumbers::contains) } or
                    rotated().any { col -> col.all(drawnNumbers::contains) }
    }
}

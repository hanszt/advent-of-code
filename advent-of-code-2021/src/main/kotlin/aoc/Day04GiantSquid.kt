package aoc

import utils.oneOrMoreWhiteSpaces
import utils.rotated
import utils.splitByBlankLine
import utils.toIntGrid
import java.io.File

internal object Day04GiantSquid : ChallengeDay {

    fun part1(path: String): Int {
        val (boards, allNrsToDrawList) = File(path).toBoardsAndNrsToDrawList()
        val drawnNrs = allNrsToDrawList.slice(0..3).toMutableList()
        var firstWinning: Array<IntArray> = emptyArray()
        while (firstWinning.isEmpty()) {
            drawnNrs.add(allNrsToDrawList[drawnNrs.size])
            firstWinning = boards.firstOrNull { it.isWinningBoard(drawnNrs) } ?: emptyArray()
        }
        return firstWinning.sumUnmarkedNrs(drawnNrs) * drawnNrs.last()
    }

    fun Array<IntArray>.isWinningBoard(drawnNumbers: List<Int>): Boolean =
        any { row -> row.all(drawnNumbers::contains) } or
                rotated().any { col -> col.all(drawnNumbers::contains) }

    fun part2(path: String): Int {
        val (boards, allNrToBeDrawn) = File(path).toBoardsAndNrsToDrawList()
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
                slice(1 until size)
                    .map { it.lines().toIntGrid(oneOrMoreWhiteSpaces) } to nrsToDrawList()
            }

    private fun List<String>.nrsToDrawList() = first().split(",").map(String::toInt)

    override fun part1() = part1(ChallengeDay.inputDir + "/day4.txt")
    override fun part2() = part2(ChallengeDay.inputDir + "/day4.txt")
}

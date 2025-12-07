package aoc.seastories

import aoc.utils.wrapBack
import java.io.File
import kotlin.math.min

/**
 * [Day 21 puzzle instructions](https://adventofcode.com/2021/day/21)
 */
internal class Day21DiracDice(inputPath: String) : ChallengeDay {

    private val startingPositions = File(inputPath).toStartingPositions()

    override fun part1(): Int = startingPositions
        .map { Player(0, position = it) }
        .let { (player1, player2) -> playGame(player1, player2) }

    override fun part2(): Long =
        startingPositions.let { (player1, player2) ->
            findMaxWinningCount(
                player1InitPos = player1,
                player2InitPos = player2,
                threshold = 21
            )
        }


    private fun playGame(player1: Player, player2: Player, winningScore: Int = 1000): Int {
        var round = 0
        val dice = Dice()
        while (player1.score < winningScore && player2.score < winningScore) {
            if (round != 0 && round % 3 == 0) {
                val player1PlayingPrevRound = player1Playing(round - 1)
                if (player1PlayingPrevRound) player1.updateScore() else player2.updateScore()
            }
            if (player1Playing(round)) player1.move(dice.roll()) else player2.move(dice.roll())
            round++
        }
        return (dice.timesRolled - 1) * min(player1.score, player2.score)
    }

    fun File.toStartingPositions() = readLines().map { it.last().digitToInt() }

    /**
     * [aoc.utils.Tag.MANY_WORLDS], [aoc.utils.Tag.RECURSIVE]
     *
     * I've not been able to solve part 2 of day 21 myself. [This solution is from the repo from Elizarov](https://github.com/elizarov/AdventOfCode2021/blob/main/src/Day21_2.kt).
     *
     * All credits go to him.
     *
     * I did a little refactoring and renaming to understand what is going on.
     *
     * It is very educational to see how such a solution can be solved. Many thanks to Roman Elizarov
     *
     * Full credits to Roman Elizarov
     */
    fun findMaxWinningCount(player1InitPos: Int, player2InitPos: Int, threshold: Int): Long {
        data class WinCounts(var player1Winnings: Long, var player2Winnings: Long)

        val boardPositions = 10
        val size = boardPositions + 1 // One extra row and column for the starting positions
        val solveSpace = Array(size) { Array(size) { Array(threshold) { arrayOfNulls<WinCounts>(threshold) } } }

        fun playDiracDice(player1Pos: Int, player2Pos: Int, player1Score: Int, player2Score: Int): WinCounts {
            solveSpace[player1Pos][player2Pos][player1Score][player2Score]?.let { return it }
            val winCounts = WinCounts(player1Winnings = 0, player2Winnings = 0)
            for (diceValue1 in 1..3) {
                for (diceValue2 in 1..3) {
                    for (diceValue3 in 1..3) {
                        val player1newPos = (player1Pos + diceValue1 + diceValue2 + diceValue3).wrapBack(1, 10)
                        val player1NewScore = player1Score + player1newPos
                        if (player1NewScore >= threshold) {
                            winCounts.player1Winnings++
                        } else {
                            val otherWinCount = playDiracDice(
                                player1Pos = player2Pos,
                                player2Pos = player1newPos,
                                player1Score = player2Score,
                                player2Score = player1NewScore
                            )
                            winCounts.player1Winnings += otherWinCount.player2Winnings
                            winCounts.player2Winnings += otherWinCount.player1Winnings
                        }
                    }
                }
            }
            solveSpace[player1Pos][player2Pos][player1Score][player2Score] = winCounts
            return winCounts
        }
        val (player1Winnings, player2Winnings) = playDiracDice(
            player1Pos = player1InitPos,
            player2Pos = player2InitPos,
            player1Score = 0,
            player2Score = 0
        )
        return maxOf(player1Winnings, player2Winnings)
    }

    data class Player(var score: Int, var position: Int) {

        fun move(steps: Int) = kotlin.run { position = (position + steps).wrapBack(1, 10) }
        fun updateScore() = kotlin.run { score += position }
    }

    data class Dice(var value: Int = 0, var timesRolled: Int = 0) {

        fun roll(): Int {
            timesRolled++
            value++
            value = value.wrapBack(1, 100)
            return value
        }
    }

    companion object {
        fun player1Playing(round: Int): Boolean = ((round) / 3) % 2 == 0
    }
}

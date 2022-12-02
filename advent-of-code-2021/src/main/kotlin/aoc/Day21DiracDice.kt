package aoc

import utils.wrapBack
import java.io.File
import kotlin.math.min

internal object Day21DiracDice : ChallengeDay {

    fun part1(path: String): Int = File(path).toStartingPositions()
        .map { Player(0, position = it) }
        .let { (player1, player2) -> playGame(player1, player2) }

    override fun part1() = part1(ChallengeDay.inputDir + "/day21.txt")

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

    fun findMaxWinningCount(player1: Player, player2: Player, winningThreshold: Int): Long {
        val winningCounts = WinCount(0, 0)
        for (newDiceValue in 1..3) {
            playGameUsingDiracDice(player1.copy(), player2.copy(), 0, newDiceValue, winningCounts, winningThreshold)
        }
        return maxOf(winningCounts.player1Winnings, winningCounts.player2Winnings)
    }

    // not working
    private fun playGameUsingDiracDice(
        player1: Player,
        player2: Player,
        round: Int,
        diceValue: Int,
        winningCounts: WinCount,
        threshold: Int
    ) {
        if (round != 0 && round % 3 == 0) {
            if (player1Playing(round - 1)) player1.updateScore() else player2.updateScore()
        }
        if (player1Playing(round)) player1.move(diceValue) else player2.move(diceValue)
        if (player1.score >= threshold || player2.score >= threshold) {
            if (player1.score > player2.score) winningCounts.player1Winnings++ else winningCounts.player2Winnings++
            return
        }
        for (newDiceValue in 1..3) {
            playGameUsingDiracDice(player1.copy(), player2.copy(), round + 1, newDiceValue, winningCounts, threshold)
        }
    }

    fun player1Playing(round: Int): Boolean = ((round) / 3) % 2 == 0

    fun part2(path: String): Long =
        File(path).toStartingPositions().let { (player1, player2) -> findMaxWinningCount(player1, player2, 21) }

    override fun part2() = part2(ChallengeDay.inputDir + "/day21.txt")

    // I've not been able to solve part 2 of day 21 myself. This solution is from the repo from Elizarov. All credits go to him.
    //
    // I did a little refactoring and renaming to understand what is going on.
    //
    // It is very educational to see how such a solution can be solved. Many thanks to Roman Elizarov
    //
    // full credits to Roman Elizarov
    //
    fun findMaxWinningCount(player1InitPos: Int, player2InitPos: Int, threshold: Int): Long {
        val size = 11
        val solveSpace = Array(size) { Array(size) { Array(threshold) { arrayOfNulls<WinCount>(threshold) } } }

        fun playDiracDice(player1Pos: Int, player2Pos: Int, player1Score: Int, player2Score: Int): WinCount {
            solveSpace[player1Pos][player2Pos][player1Score][player2Score]?.let { return it }
            val winCount = WinCount(0, 0)
            for (diceValue1 in 1..3) {
                for (diceValue2 in 1..3) {
                    for (diceValue3 in 1..3) {
                        val player1newPos = (player1Pos + diceValue1 + diceValue2 + diceValue3).wrapBack(1, 10)
                        val player1NewScore = player1Score + player1newPos
                        if (player1NewScore >= threshold) {
                            winCount.player1Winnings++
                        } else {
                            val otherWinCount = playDiracDice(player2Pos, player1newPos, player2Score, player1NewScore)
                            winCount.player1Winnings += otherWinCount.player2Winnings
                            winCount.player2Winnings += otherWinCount.player1Winnings
                        }
                    }
                }
            }
            solveSpace[player1Pos][player2Pos][player1Score][player2Score] = winCount
            return winCount
        }

        val (player1Winnings, player2Winnings) = playDiracDice(player1InitPos, player2InitPos, 0, 0)
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

    data class WinCount(var player1Winnings: Long, var player2Winnings: Long)
}

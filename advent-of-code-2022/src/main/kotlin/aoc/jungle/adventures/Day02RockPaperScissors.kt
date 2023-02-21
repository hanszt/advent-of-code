package aoc.jungle.adventures

import aoc.utils.ChallengeDay
import java.io.File

/**
 * @see <a href="https://adventofcode.com/2022/day/2">Day 2: Rock paper scissors</a>
 */
class Day02RockPaperScissors(fileName: String) : ChallengeDay {

    private val lines: List<String> = File(fileName).readLines()

    override fun part1(): Int = lines.fold(0) { totalScore, line ->
        val (opponent, self) = line.split(" ").map { it.toType() }
        totalScore + determineScore(opponent, self) + self
    }

    private fun determineScore(opponent: Int, self: Int): Int = when {
        self == paper && opponent == rock -> win
        self == scissors && opponent == paper -> win
        self == rock && opponent == scissors -> win
        self == opponent -> draw
        else -> lost
    }

    override fun part2(): Int = lines.fold(0) { totalScore, line ->
        val (opponent, requiredOutcome) = line.split(" ")
        val score = requiredOutcome.toScore()
        totalScore + score + determineSelf(opponent.toType(), score)
    }

    private fun String.toScore(): Int = when(this) {
        "X" -> lost
        "Y" -> draw
        "Z" -> win
        else -> error("$this unknown")
    }

    private fun String.toType() = when(this) {
        "A", "X" -> rock
        "B", "Y" -> paper
        "C", "Z" -> scissors
        else -> error("$this unknown type")
    }

    private fun determineSelf(opponent: Int, outcome: Int) = when (outcome) {
        draw -> opponent
        win -> determineSelfWhenWon(opponent)
        else -> determineSelfWhenLost(opponent)
    }

    private fun determineSelfWhenWon(opponent: Int) = if (opponent + 1 > 3) 1 else opponent + 1
    private fun determineSelfWhenLost(opponent: Int) = if (opponent - 1 < 1) 3 else opponent - 1

    companion object {

        private const val lost = 0
        private const val draw = 3
        private const val win = 6

        private const val rock = 1
        private const val paper = 2
        private const val scissors = 3
    }
}
